package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.PostDTO;
import org.example.domain.PostHandlingDTO;
import org.example.entity.Post;
import org.example.mapper.MapStructMapper;
import org.example.repository.PostRepository;
import org.example.service.CommentService;
import org.example.service.PostService;
import org.example.service.RateService;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CommunityController {
    private final UserService userService;
    private final PostService postService;
    private final RateService rateService;
    private final CommentService commentService;

    @RequestMapping(value = "/admin/community", method = RequestMethod.GET)
    public String communityPage(Model model){
        userService.addUserAttributesToModel(model);
        List<PostDTO> posts = postService.getPosts();
        for(PostDTO post : posts){
            post.setPost_id(post.getId().toString());
            float average_star = rateService.calculateAverageRateInPost(post);
            String star_text = String.format("%.1f", average_star).replace('.', ',');
            String[] starRatings = rateService.calculateStarRatings(average_star);
            String people_rates = rateService.countRateByPost(post.getId());
            model.addAttribute("average_star_" + post.getPost_id(), star_text);
            model.addAttribute("people_rates_" + post.getPost_id(), people_rates);
            model.addAttribute("star_rating_" + post.getPost_id(), starRatings);
        }
        model.addAttribute("post_create", new PostHandlingDTO());
        model.addAttribute("posts",posts);
        return "admin/community";
    }

    @RequestMapping(value = "/admin/provide-rating/{id}", method = RequestMethod.POST)
    public String provideRating(@PathVariable("id") UUID post_id, @RequestParam("star") int star){
        rateService.updateRatingForPost(post_id,star);
        return "redirect:/admin/community";
    }

    @RequestMapping(value = "/admin/write-comment/{id}", method = RequestMethod.POST)
    public String writeComment(@PathVariable("id") UUID post_id, @RequestParam("comment") String comment_text){
        commentService.writeCommentForPost(post_id, comment_text);
        return "redirect:/admin/community";
    }
}
