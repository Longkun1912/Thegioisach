package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.example.domain.*;
import org.example.entity.*;
import org.example.mapper.MapStructMapper;
import org.example.repository.*;
import org.example.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final MapStructMapper mapper;
    private final PasswordEncoder passwordEncoder;

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
            userService.addUserAttributesToModel(model);
            return "admin/dashboard";
        }
    }

    @GetMapping(value = "/user-index")
    public String userIndex(Model model){
        userService.addUserAttributesToModel(model);
        Role role = roleRepository.findRoleByName("user");
        List<User> users = userRepository.findUserByRole(role);
        List<UserDetailsDTO> userDetailsDTOS = new ArrayList<>();
        for (User user : users){
            // Configure user image
            byte[] userImage = user.getImage();
            Tika tika = new Tika();
            String mimeType = tika.detect(userImage);
            String base64EncodedImage = Base64.getEncoder().encodeToString(userImage);
            UserImageData userImageData = new UserImageData(user, mimeType, base64EncodedImage);
            UserDetailsDTO userDetailsDTO = mapper.userDetailsDto(user);
            userDetailsDTO.setUserImageData(userImageData);
            // Configure account update
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
        userService.updateModel(model);
        model.addAttribute("user",new UserDTO());
        return "admin/add_user";
    }

    @PostMapping(value = "/add-user")
    public String addUserForm(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult result, Model model,
                              @RequestParam(value = "file",required = false) MultipartFile file) throws IOException{
        if(result.hasErrors()){
            userService.updateModel(model);
            return "admin/add_user";
        }
        else if (userRepository.findUserByName(userDTO.getUsername()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("username",null,"Username already exists.");
            return "admin/add_user";
        }
        else if (userRepository.findUserByEmail(userDTO.getEmail()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("email",null,"Email already exists.");
            return "admin/add_user";
        }
        else if (userRepository.findUserByPhoneNumber(userDTO.getPhone_number()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("phone_number",null,"Phone number already exists.");
            return "admin/add_user";
        }
        else if(!Objects.equals(userDTO.getPassword(), userDTO.getConfirm_password())){
            userService.updateModel(model);
            result.rejectValue("confirm_password",null,"Confirm password does not match.");
            return "admin/add_user";
        }
        else if(file == null || file.isEmpty()){
            userService.updateModel(model);
            result.rejectValue("image",null,"Please upload your image.");
            return "admin/add_user";
        }
        else if(!Objects.requireNonNull(file.getContentType()).startsWith("image/")){
            userService.updateModel(model);
            result.rejectValue("image",null,"Please upload only image files.");
            return "admin/add_user";
        }
        else {
            User user = mapper.userDtoToUser(userDTO);
            user.setId(UUID.randomUUID());
            // Get the original filename of the uploaded file
            String originalFilename = file.getOriginalFilename();
            // Extract the file extension
            String extension = FilenameUtils.getExtension(originalFilename);
            // Construct a new filename based on the user's username
            String newFilename = userDTO.getUsername() + "." + extension;
            // Save the uploaded file to the specified directory
            Path path = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\img\\user\\" + newFilename);
            if (userDTO.getInput_role().contains("admin")){
                path = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\img\\admin\\" + newFilename);
            }
            // Set the image attribute of the User object to the new filename
            user.setImage(file.getBytes());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setRole(roleRepository.findRoleByName(userDTO.getInput_role()));
            user.setLast_updated(LocalDateTime.now());
            userRepository.save(user);
            Files.write(path, file.getBytes());
            return "redirect:/admin/user-index";
        }
    }

    @GetMapping(value = "/edit-user/{id}")
    public String editUser(@PathVariable("id") UUID user_id, Model model){
        userService.updateModel(model);
        Optional<User> existing_user = Optional.of(userRepository.findById(user_id).orElseThrow());
        UserDTO userDTO = mapper.userDto(existing_user.get());
        model.addAttribute("edit_user", userDTO);
        return "admin/edit_user";
    }

    @PostMapping(value = "/edit-user")
    public String editUserForm(@ModelAttribute("edit_user") @Valid UserDTO userDTO, BindingResult result, Model model,
                               @RequestParam(value = "file",required = false) MultipartFile file) throws IOException{
        if(result.hasErrors()){
            userService.updateModel(model);
            return "admin/edit_user";
        }
        else if (userRepository.findUserByName(userDTO.getUsername()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("username",null,"Username already exists.");
            return "admin/edit_user";
        }
        else if (userRepository.findUserByEmail(userDTO.getEmail()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("email",null,"Email already exists.");
            return "admin/edit_user";
        }
        else if (userRepository.findUserByPhoneNumber(userDTO.getPhone_number()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("phone_number",null,"Phone number already exists.");
            return "admin/edit_user";
        }
        else if(!Objects.equals(userDTO.getPassword(), userDTO.getConfirm_password())){
            userService.updateModel(model);
            result.rejectValue("confirm_password",null,"Confirm password does not match.");
            return "admin/edit_user";
        }
        else if(file == null || file.isEmpty()){
            userService.updateModel(model);
            result.rejectValue("image",null,"Please upload your image.");
            return "admin/edit_user";
        }
        else if(!Objects.requireNonNull(file.getContentType()).startsWith("image/")){
            userService.updateModel(model);
            result.rejectValue("image",null,"Please upload only image files.");
            return "admin/edit_user";
        }
        else {
            Optional<User> existed_user = userRepository.findById(userDTO.getId());
            if(existed_user.isPresent()){
                // Delete user old image from file path
                userService.deleteUserImagePath(existed_user.get().getUsername());
                // Get the original filename of the uploaded file
                String originalFilename = file.getOriginalFilename();
                // Extract the file extension
                String extension = FilenameUtils.getExtension(originalFilename);
                // Construct a new filename based on the user's username
                String newFilename = userDTO.getUsername() + "." + extension;
                // Save the uploaded file to the specified directory
                Path path = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\img\\user\\" + newFilename);
                if (userDTO.getInput_role().contains("admin")){
                    path = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\img\\admin\\" + newFilename);
                }
                // Map User entity and save to database
                User updated_user = mapper.userDtoToUser(userDTO);
                updated_user.setId(userDTO.getId());
                updated_user.setRole(roleRepository.findRoleByName(userDTO.getInput_role()));
                updated_user.setLast_updated(LocalDateTime.now());
                // Set the image attribute of the User object to the new filename
                updated_user.setImage(file.getBytes());
                updated_user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                userRepository.save(updated_user);
                Files.write(path, file.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
            return "redirect:/admin/user-index";
        }
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") UUID user_id){
        Optional<User> user = Optional.of(userRepository.findById(user_id).orElseThrow());
        userService.deleteUserImagePath(user.get().getUsername());
        userRepository.delete(user.get());
        return "redirect:/admin/user-index";
    }
}
