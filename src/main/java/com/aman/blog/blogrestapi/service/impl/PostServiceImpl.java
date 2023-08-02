package com.aman.blog.blogrestapi.service.impl;

import com.aman.blog.blogrestapi.entity.Post;
import com.aman.blog.blogrestapi.exceptions.ResourceNotFoundException;
import com.aman.blog.blogrestapi.payload.PostDto;
import com.aman.blog.blogrestapi.repository.PostRepository;
import com.aman.blog.blogrestapi.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        //convert DTO to entity;
        Post post=mapToPost(postDto);
        Post newPost= postRepository.save(post);
        //convert entity to DTO

        PostDto postResponse=mapToDto(newPost);
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> posts=postRepository.findAll();
        return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","title",id));
        return mapToDto(post);
    }

    //convert entity to dto
    private PostDto mapToDto(Post post){
        PostDto postDto=new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }
    private Post mapToPost(PostDto postDto){
        Post post=new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}
