package org.example.service;

import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.example.domain.PostDTO;
import org.example.domain.PostImageData;
import org.example.domain.UserDetailsDTO;
import org.example.domain.UserImageData;
import org.example.entity.Post;
import org.example.entity.User;
import org.example.mapper.MapStructMapper;
import org.example.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MapStructMapper mapper;

    public List<PostDTO> getPosts(){
        List<Post> posts = postRepository.findPostsOrderByTime();
        List<PostDTO> community_posts = new ArrayList<>();
        for (Post post : posts){
            User creator = post.getCreator();
            //Declare byte[] for creator image
            byte[] postCreatorImage = creator.getImage();
            Tika tika = new Tika();
            // Configure for post creator image
            String creatorMimeType = tika.detect(postCreatorImage);
            String creatorBase64EncodedImage = Base64.getEncoder().encodeToString(postCreatorImage);
            UserImageData creatorImageData = new UserImageData(creator, creatorMimeType, creatorBase64EncodedImage);
            // Mapper for post image
            PostDTO postDTO = mapper.postDto(post);
            // Format created_time for post
            LocalDateTime created_time = post.getCreated_time();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy 'at' hh:mm a");
            postDTO.setLast_updated(created_time.format(formatter));
            // Mapper for post creator image
            UserDetailsDTO creator_detail = mapper.userDetailsDto(creator);
            creator_detail.setUserImageData(creatorImageData);
            // Set creator image for post
            postDTO.setCreator_detail(creator_detail);
            // Configure for post that contains image as content
            if(post.getContent_image()!=null){
                //Declare byte[] for post image
                byte[] postContentImage = post.getContent_image();
                // Configure for post image
                String postMimeType = tika.detect(postContentImage);
                String postBase64EncodedImage = Base64.getEncoder().encodeToString(postContentImage);
                PostImageData postImageData = new PostImageData(post,postMimeType,postBase64EncodedImage);
                postDTO.setPostImageData(postImageData);
            }
            community_posts.add(postDTO);
        }
        return community_posts;
    }
}
