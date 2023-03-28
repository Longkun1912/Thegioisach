package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.example.domain.UserAddingDTO;
import org.example.domain.UserDetailsDTO;
import org.example.domain.UserImageData;
import org.example.domain.UserRegisterDTO;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.mapper.MapStructMapper;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MapStructMapper mapper;

    @GetMapping(value = "/dashboard")
    public String register(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: "+auth);

        String access_role = auth.getAuthorities().toString();
        System.out.println("Access role: "+access_role);

        if(!access_role.contains("admin")){
            return "forbidden_page";
        }
        else {
            addUserAttributesToModel(model);
            return "admin/dashboard";
        }
    }

    @GetMapping(value = "/user-index")
    public String userIndex(Model model){
        addUserAttributesToModel(model);
        Role role = roleRepository.findRoleByName("user");
        List<User> users = userRepository.findUserByRole(role);
        List<UserDetailsDTO> userDetailsDTOS = new ArrayList<>();
        for (User user : users){
            byte[] userImage = user.getImage();
            Tika tika = new Tika();
            String mimeType = tika.detect(userImage);
            String base64EncodedImage = Base64.getEncoder().encodeToString(userImage);
            UserImageData userImageData = new UserImageData(user, mimeType, base64EncodedImage);
            UserDetailsDTO userDetailsDTO = mapper.userDetailsDto(user);
            userDetailsDTO.setUserImageData(userImageData);
            LocalDateTime last_updated = user.getLast_updated();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy 'at' hh:mm a");
            userDetailsDTO.setLast_updated(last_updated.format(formatter));
            userDetailsDTOS.add(userDetailsDTO);
        }
        model.addAttribute("users",userDetailsDTOS);
        return "admin/user_index";
    }

    @GetMapping(value = "/add-user")
    public String addUser(Model model){
        addUserAttributesToModel(model);
        model.addAttribute("user",new UserAddingDTO());
        return "admin/add_user";
    }

    public void addUserAttributesToModel(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(auth.getName()).get();
        String role = user.getRole().getName();
        model.addAttribute("user", user);
        model.addAttribute("role", role);
    }
}
