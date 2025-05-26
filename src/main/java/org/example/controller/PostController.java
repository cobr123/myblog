package org.example.controller;

import org.example.model.Post;
import org.example.model.Posts;
import org.example.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public String posts(
            Model model,
            @RequestParam(required = false, defaultValue = "", name = "search") String search,
            @RequestParam(required = false, defaultValue = "10", name = "pageSize") int pageSize,
            @RequestParam(required = false, defaultValue = "1", name = "pageNumber") int pageNumber
    ) {
        Posts posts = service.findAll(search, pageSize, pageNumber);
        model.addAttribute("posts", posts);

        return "posts";
    }

    @GetMapping("/{id}")
    public String post(Model model, @PathVariable(name = "id") Long id) {
        Post post = service.findById(id);
        model.addAttribute("post", post);

        return "post";
    }

    @GetMapping("/add")
    public String post() {
        return "add-post";
    }

    @PostMapping
    public String save(@ModelAttribute Post post) {
        service.save(post);

        return "redirect:/posts";
    }

    @PostMapping(value = "/{id}", params = "_method=delete")
    public String delete(@PathVariable(name = "id") Long id) {
        service.deleteById(id);

        return "redirect:/posts";
    }
}