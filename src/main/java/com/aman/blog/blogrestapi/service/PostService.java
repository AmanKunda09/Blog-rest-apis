package com.aman.blog.blogrestapi.service;


import com.aman.blog.blogrestapi.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPost();
    PostDto getPostById(Long id);
}
