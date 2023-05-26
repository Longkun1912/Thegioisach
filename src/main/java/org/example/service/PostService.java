package org.example.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.example.domain.*;
import org.example.entity.Post;
import org.example.entity.User;
import org.example.mapper.MapStructMapper;
import org.example.repository.PostRepository;
import org.example.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final FileService fileService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final MapStructMapper mapper;

    public List<PostDTO> getPosts(){
        List<Post> posts = postRepository.findPostsOrderByTime();
        List<PostDTO> community_posts = new ArrayList<>();
        for (Post post : posts){
            User creator = post.getCreator();
            // Mapper for post image
            PostDTO postDTO = mapper.postDto(post);
            // Format created_time for post
            LocalDateTime created_time = post.getCreated_time();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy 'at' hh:mm a");
            postDTO.setLast_updated(created_time.format(formatter));
            // Mapper for post creator image
            UserDetailsDTO creator_detail = mapper.userDetailsDto(creator);
            // Set creator image for post
            postDTO.setCreator_detail(creator_detail);
            // Configure for post that contains image as content
            community_posts.add(postDTO);
        }
        return community_posts;
    }

    public void saveNewPost(PostHandlingDTO postHandlingDTO, MultipartFile file) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(auth.getName()).get();
        Post post = mapper.postHandlingDtoToPost(postHandlingDTO);
        post.setId(UUID.randomUUID());
        post.setCreator(user);
        post.setTitle(postHandlingDTO.getTitle());
        post.setContent_text(postHandlingDTO.getContent_text());
        post.setCreated_time(LocalDateTime.now());
        postRepository.save(post);
    }

    public void saveUpdatedPost(UUID post_id,String title, String content_text, MultipartFile file) throws IOException {
        Post post = postRepository.findById(post_id).orElseThrow();
        Path path = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\img\\post\\");
        fileService.deleteFileIfExists(path, post.getId().toString());
        if (file != null){
            System.out.println("File is found!");
            // Get the original filename of the uploaded file
            String originalFilename = file.getOriginalFilename();
            // Extract the file extension
            String extension = FilenameUtils.getExtension(originalFilename);
            // Construct a new filename based on the post id
            String newFilename = post.getId().toString() + "." + extension;
            // Save the uploaded file to the specified directory
            Path post_path = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\img\\post\\" + newFilename);
            Files.write(post_path, file.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
        post.setTitle(title);
        post.setContent_text(content_text);
        post.setCreated_time(LocalDateTime.now());
        postRepository.save(post);
    }

    public void deletePost(UUID post_id){
        Post post = postRepository.findById(post_id).orElseThrow();
        Path path = Paths.get("D:\\thegioisach\\src\\main\\resources\\static\\img\\post\\");
        fileService.deleteFileIfExists(path, post.getId().toString());
        postRepository.delete(post);
    }
}
