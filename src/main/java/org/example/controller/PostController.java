package org.example.controller;

import org.example.model.Comment;
import org.example.model.Post;
import org.example.model.Posts;
import org.example.service.CommentService;
import org.example.service.PostService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
    public String getAll(
            Model model,
            @RequestParam(required = false, defaultValue = "", name = "search") String search,
            @RequestParam(required = false, defaultValue = "10", name = "pageSize") int pageSize,
            @RequestParam(required = false, defaultValue = "1", name = "pageNumber") int pageNumber
    ) {
        Posts posts = postService.findAll(search, pageSize, pageNumber);
        for (Post post : posts.posts()) {
            List<Comment> comments = commentService.findByPostId(post.getId());
            post.setComments(comments);
        }
        model.addAttribute("posts", posts);

        return "posts";
    }

    @GetMapping("/{id}")
    public String get(Model model, @PathVariable("id") Long id) {
        Post post = postService.findById(id);
        List<Comment> comments = commentService.findByPostId(post.getId());
        post.setComments(comments);
        model.addAttribute("post", post);

        return "post";
    }

    @GetMapping("/add")
    public String getForm() {
        return "add-post";
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String insert(@RequestParam("title") String title, @RequestParam("text") String text, @RequestParam("image") MultipartFile image, @RequestParam("tags") String tags) throws IOException {
        File tempFile = File.createTempFile("prefix-", "-suffix");
        Files.write(tempFile.toPath(), image.getBytes());
        tempFile.deleteOnExit();

        Post post = new Post();
        post.setTitle(title);
        post.setText(text);
        post.setTags(tags);
        post.setImagePath(tempFile.getAbsolutePath());
        Long id = postService.insert(post);

        return "redirect:/posts/" + id;
    }

    @PostMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String update(@RequestParam("id") Long id, @RequestParam("title") String title, @RequestParam("text") String text, @RequestParam("image") MultipartFile image, @RequestParam("tags") String tags) throws IOException {
        Post post = postService.findById(id);

        if (image != null) {
            File tempFile = File.createTempFile("prefix-", "-suffix");
            Files.write(tempFile.toPath(), image.getBytes());
            tempFile.deleteOnExit();
            post.setImagePath(tempFile.getAbsolutePath());
        }
        post.setTitle(title);
        post.setText(text);
        post.setTags(tags);
        postService.update(post);

        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/like")
    public String like(@PathVariable("id") Long id, @PathVariable("like") boolean like) {
        Post post = postService.findById(id);
        if (like) {
            post.setLikesCount(post.getLikesCount() + 1);
        } else {
            post.setLikesCount(post.getLikesCount() - 1);
        }
        postService.update(post);

        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        Post post = postService.findById(id);
        List<Comment> comments = commentService.findByPostId(post.getId());
        post.setComments(comments);
        model.addAttribute("post", post);

        return "add-post";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        postService.deleteById(id);
        commentService.deleteByPostId(id);

        return "redirect:/posts";
    }
}