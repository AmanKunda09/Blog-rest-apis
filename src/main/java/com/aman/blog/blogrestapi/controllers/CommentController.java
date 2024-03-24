package com.aman.blog.blogrestapi.controllers;

import com.aman.blog.blogrestapi.payload.CommentDto;
import com.aman.blog.blogrestapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,@RequestBody CommentDto commentDto){

        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getAllCommentByPostId(@PathVariable(value = "postId") long postId){
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId),HttpStatus.OK);
    }
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentByCommentId(@PathVariable(value = "id") long commentId,
                                                            @PathVariable(value = "postId") long postId)
    {
        CommentDto commentDto=commentService.getCommentById(postId,commentId);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable long postId,@PathVariable(value = "id") long commentId,@RequestBody CommentDto commentDto)
    {
        CommentDto updatedComment=commentService.updateComment(postId, commentId, commentDto);
        return new ResponseEntity<>(updatedComment,HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable long postId,@PathVariable(value = "id") long commentId){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("comment deleted",HttpStatus.OK);
    }

}
