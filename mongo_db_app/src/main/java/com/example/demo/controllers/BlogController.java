package com.example.demo.controllers;

import com.example.demo.models.blog.Post;
import com.example.demo.models.person.Person;
import com.example.demo.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping("get-post")
    public List<Post> getAllPosts() {
        return blogService.getAllPost();
    }

    @GetMapping("add-post")
    public ResponseEntity<?> addPost() {
        blogService.addPost();
        return ResponseEntity.ok("Success");
    }

    @GetMapping("fullTextSearch")
    public ResponseEntity<?> fullTextSearch() {
        List<Post> posts = blogService.fullTextSearch("teg1");
        return ResponseEntity.ok(posts);
    }

    @GetMapping("findPostByAggregation")
    public ResponseEntity<?> findPostByAggregation() {
        List<Post> posts = blogService.findPostByAggregation("category1");
        return ResponseEntity.ok(posts);
    }

    @GetMapping("findPostWithPagination")
    public ResponseEntity<?> findPostWithPagination() {
        List<Post> posts = blogService.findPostWithPagination(1, 2);
        return ResponseEntity.ok(posts);
        
    }

    @GetMapping("get-post-without-tags")
    public List<Post> getAllPostWithoutTags() {
        return blogService.getAllPostWithoutTags();
    }
}
