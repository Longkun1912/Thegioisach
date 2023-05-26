package org.example.service;

import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public Set<SimpleGrantedAuthority> getRole(Optional<User> user) {
        Role role = user.get().getRole();
        return Collections.singleton(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.of(userRepository.findUserByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("Username " + username + " not found!")));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.get().getEmail(),
                user.get().getPassword(), getRole(user));
        return userDetails;
    }

    public List<String> getUserStatus(){
        List<String> status = new ArrayList<>();
        status.add("Enabled");
        status.add("Disabled");
        return status;
    }

    public List<String> getUserRole(){
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    public void addUserAttributesToModel(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(auth.getName()).get();
        String role = user.getRole().getName();
        model.addAttribute("user_detail", user);
        model.addAttribute("role", role);
    }

    public void updateModel(Model model){
        addUserAttributesToModel(model);
        model.addAttribute("status",getUserStatus());
        model.addAttribute("roles", getUserRole());
    }

    public void deleteUserImagePath(String username){
        Path imagePath = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\img\\user");
        try (Stream<Path> walk = Files.walk(imagePath)) {
            Optional<Path> image = walk
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().contains(username))
                    .findFirst();
            if (image.isPresent()) {
                Files.delete(image.get());
            } else {
                System.out.println("Image not found");
            }
        } catch (IOException e) {
            System.out.println("Error.");
        }
    }
}
