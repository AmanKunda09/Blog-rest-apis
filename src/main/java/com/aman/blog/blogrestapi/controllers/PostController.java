package com.aman.blog.blogrestapi.controllers;

import com.aman.blog.blogrestapi.payload.PostDto;
import com.aman.blog.blogrestapi.payload.PostResponse;
import com.aman.blog.blogrestapi.service.PostService;
import com.aman.blog.blogrestapi.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){

        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @GetMapping
    public PostResponse getAllPost(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir
            ){
        return postService.getAllPost(pageNo, pageSize,sortBy,sortDir);
    }
    @GetMapping("/{id}")
        public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
        return  new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto,@PathVariable long id){

        PostDto postResponse=postService.updatePostById(postDto,id);

        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id){
        postService.deletePostByid(id);
        return new ResponseEntity<>("The post has been successfully deleted",HttpStatus.OK);
    }

}
