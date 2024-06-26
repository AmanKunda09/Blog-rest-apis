package com.aman.blog.blogrestapi.service.impl;

import com.aman.blog.blogrestapi.entity.Comment;
import com.aman.blog.blogrestapi.entity.Post;
import com.aman.blog.blogrestapi.exceptions.BlogAPIException;
import com.aman.blog.blogrestapi.exceptions.ResourceNotFoundException;
import com.aman.blog.blogrestapi.payload.CommentDto;
import com.aman.blog.blogrestapi.repository.CommentRepository;
import com.aman.blog.blogrestapi.repository.PostRepository;
import com.aman.blog.blogrestapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId,CommentDto commentDto) {
        Comment comment=mapToEntity(commentDto);
        //retrieve post entity by id
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",postId));
        //set post to comment entity
        comment.setPost(post);
        //save comment to db
        Comment newComment=commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments=commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId,long commentId) {
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId,CommentDto commetRequest) {
        Post post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id",postId));
        Comment comment=commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id",commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        comment.setName(commetRequest.getName());
        comment.setEmail(commetRequest.getEmail());
        comment.setBody(commetRequest.getBody());

        Comment updatedComment=commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id",postId));
        Comment comment=commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id",commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        commentRepository.delete(comment);
    }


    private Comment mapToEntity(CommentDto commentDto){
        Comment comment=new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        return comment;
    }
    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto=new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setBody(comment.getBody());
        commentDto.setEmail(comment.getEmail());
        return commentDto;
    }
}
