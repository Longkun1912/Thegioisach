package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.PostHandlingDTO;
import org.example.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @RequestMapping(path = "/admin/create-post", method = RequestMethod.POST)
    public String createPost(@ModelAttribute("post_create") PostHandlingDTO postHandlingDTO,
                             @RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
        postService.saveNewPost(postHandlingDTO,file);
        return "redirect:/admin/community";
    }

    @RequestMapping(path = "/admin/edit-post/{id}", method = RequestMethod.POST)
    public String editPost(@PathVariable("id") UUID post_id,
                           @RequestParam("title") String title,
                           @RequestParam("content_text") String content_text,
                           @RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
        postService.saveUpdatedPost(post_id,title,content_text,file);
        return "redirect:/admin/community";
    }

    @RequestMapping(path = "/admin/delete-post/{id}", method = RequestMethod.GET)
    public String deletePost(@PathVariable("id") UUID post_id){
        postService.deletePost(post_id);
        return "redirect:/admin/community";
    }
}
