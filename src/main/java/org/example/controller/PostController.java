package org.example.controller;

import org.example.model.Comment;
import org.example.model.Post;
import org.example.model.Posts;
import org.example.service.CommentService;
import org.example.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping
    public String posts(
            Model model,
            @RequestParam(required = false, defaultValue = "", name = "search") String search,
            @RequestParam(required = false, defaultValue = "10", name = "pageSize") int pageSize,
            @RequestParam(required = false, defaultValue = "1", name = "pageNumber") int pageNumber
    ) {
        Posts posts = postService.findAll(search, pageSize, pageNumber);
        for (Post post: posts.posts()) {
            List<Comment> comments = commentService.findByPostId(post.getId());
            post.setComments(comments);
        }
        model.addAttribute("posts", posts);

        return "posts";
    }

    @GetMapping("/{id}")
    public String post(Model model, @PathVariable(name = "id") Long id) {
        Post post = postService.findById(id);
        List<Comment> comments = commentService.findByPostId(post.getId());
        post.setComments(comments);
        model.addAttribute("post", post);

        return "post";
    }

    @GetMapping("/add")
    public String post() {
        return "add-post";
    }

    @PostMapping
    public String insertPost(@ModelAttribute Post post) {
        postService.insert(post);

        return "redirect:/posts";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id) {
        postService.deleteById(id);

        return "redirect:/posts";
    }
}