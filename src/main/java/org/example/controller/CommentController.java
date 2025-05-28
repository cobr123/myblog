package org.example.controller;

import org.example.model.Comment;
import org.example.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping
    public String add(@PathVariable("postId") Long postId, @RequestParam("text") String text) {
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setText(text);
        service.insert(comment);

        return "redirect:/posts/" + postId;
    }

    @PostMapping("/{commentId}")
    public String edit(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId, @RequestParam("text") String text) {
        Comment comment = service.findById(commentId);
        comment.setText(text);
        service.update(comment);

        return "redirect:/posts/" + postId;
    }

    @PostMapping("/{commentId}/delete")
    public String delete(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
        service.deleteById(commentId);

        return "redirect:/posts/" + postId;
    }
}