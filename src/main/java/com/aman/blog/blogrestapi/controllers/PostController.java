package com.aman.blog.blogrestapi.controllers;

import com.aman.blog.blogrestapi.payload.PostDto;
import com.aman.blog.blogrestapi.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
  private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    //create blog post
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){

        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPost(){
        return new ResponseEntity<>(postService.getAllPost(),HttpStatus.OK);
    }
    @GetMapping("/{id}")
        public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id){
        return  new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK);
    }

}
