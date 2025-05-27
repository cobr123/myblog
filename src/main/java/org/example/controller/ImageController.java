package org.example.controller;

import org.example.model.Post;
import org.example.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@RequestMapping("/images")
public class ImageController {

    private final PostService service;

    public ImageController(PostService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public byte[] get(@PathVariable(name = "id") Long id) throws IOException {
        Post post = service.findById(id);
        return Files.readAllBytes(Paths.get(post.getImagePath()));
    }
}
