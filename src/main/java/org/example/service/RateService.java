package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.PostDTO;
import org.example.entity.Post;
import org.example.entity.Rate;
import org.example.entity.User;
import org.example.repository.PostRepository;
import org.example.repository.RateRepository;
import org.example.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RateService {
    private final RateRepository rateRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public float calculateAverageRateInPost(PostDTO postDTO){
        float average_rate = rateRepository.getAverageRateForPost(postDTO.getId());
        // Rounded average star rate to the nearest 0.5
        float rounded_rate = Math.round(average_rate * 2) / 2.0f;
        System.out.println("Average rate of post '" + postDTO.getTitle() + "' is: " + rounded_rate);
        postDTO.setAverage_rate(rounded_rate);
        return rounded_rate;
    }

    public void updateRatingForPost(UUID post_id, int rate_value){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(auth.getName()).get();
        Optional<Rate> existed_rate = rateRepository.findRateByUserAndPost(post_id,user);
        if(existed_rate.isPresent()){
            existed_rate.get().setRate_value(rate_value);
            existed_rate.get().setCreated_time(LocalDateTime.now());
            rateRepository.save(existed_rate.get());
        }
        else {
            Optional<Post> post = postRepository.findById(post_id);
            Rate rate = new Rate(user,post.get());
            rate.setRate_value(rate_value);
            rate.setCreated_time(LocalDateTime.now());
            rateRepository.save(rate);
        }
    }

    public String[] calculateStarRatings(float average_star) {
        String[] starRatings = new String[5];
        int fullStars = (int) average_star;
        boolean hasHalfStar = (average_star - fullStars) >= 0.5f;
        for (int i = 0; i < fullStars; i++) {
            starRatings[i] = "checked";
        }
        if (hasHalfStar) {
            starRatings[fullStars] = "half";
        }
        for (int i = fullStars + (hasHalfStar ? 1 : 0); i < 5; i++) {
            starRatings[i] = "unchecked";
        }
        return starRatings;
    }

    public String countRateByPost(UUID post_id){
        return rateRepository.countRateByPost(post_id).toString();
    }

}
