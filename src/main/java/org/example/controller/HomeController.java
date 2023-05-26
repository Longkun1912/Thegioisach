package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.example.domain.UserRegisterDTO;
import org.example.entity.User;
import org.example.mapper.MapStructMapper;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.example.security.jwt.JwtTokenProvider;
import org.example.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MapStructMapper mapper;
    private final RoleRepository roleRepository;

    @GetMapping(value = "/")
    public String homeIndex(){
        return "home";
    }

    @GetMapping(value = "/register")
    public String register(Model model){
        model.addAttribute("user",new UserRegisterDTO());
        return "register";
    }

    @PostMapping(value = "/register")
    public String registerForm(@ModelAttribute("user") @Valid UserRegisterDTO userRegisterDTO, BindingResult result,
                               @RequestParam(value = "termCheck",required = false) Boolean termCheck,
                               @RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
        if(result.hasErrors()){
            return "register";
        }
        else if (userRepository.findUserByName(userRegisterDTO.getUsername()).isPresent()) {
            result.rejectValue("username",null,"Username already exists.");
            return "register";
        }
        else if (userRepository.findUserByEmail(userRegisterDTO.getEmail()).isPresent()) {
            result.rejectValue("email",null,"Email already exists.");
            return "register";
        }
        else if (userRepository.findUserByPhoneNumber(userRegisterDTO.getPhone_number()).isPresent()) {
            result.rejectValue("phone_number",null,"Phone number already exists.");
            return "register";
        }
        else if(!Objects.equals(userRegisterDTO.getPassword(), userRegisterDTO.getConfirm_password())){
            result.rejectValue("confirm_password",null,"Confirm password does not match.");
            return "register";
        }
        else if(file == null || file.isEmpty()){
            result.rejectValue("image",null,"Please upload your image.");
            return "register";
        }
        else if(!Objects.requireNonNull(file.getContentType()).startsWith("image/")){
            result.rejectValue("image",null,"Please upload only image files.");
            return "register";
        }
        else if(termCheck == null || !termCheck){
            return "register";
        }
        else {
            User user = mapper.userRegisteredDtoToUser(userRegisterDTO);
            user.setId(UUID.randomUUID());
            user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
            user.setRole(roleRepository.findRoleByName("user"));
            user.setLast_updated(LocalDateTime.now());
            userRepository.save(user);
            System.out.println(user);
            user = null;
            return "home";
        }
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/login-success")
    public String loginSuccess(Authentication authentication){
        if(authentication != null){
            final UserDetails userDetails = userService
                    .loadUserByUsername(authentication.getName());
            System.out.println("Account logged in: "+userDetails);

            final String token = jwtTokenProvider.generateToken(userDetails);
            System.out.println("Logged in: "+userDetails.getUsername()+"; token: "+token);

            String role = authentication.getAuthorities().toString();
            System.out.println("User role: "+role);

            if(role.contains("admin")){
                return "redirect:/admin/dashboard";
            }
            else if (role.contains("user")){
                return "redirect:/user/home-page";
            }
            else{
                return "error_page";
            }
        } else {
            return "error_page";
        }
    }

    @GetMapping(value = "/logout")
    public String logOut(){
        return "home";
    }
}
