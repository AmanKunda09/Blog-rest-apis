package com.aman.blog.blogrestapi.service;


import com.aman.blog.blogrestapi.payload.PostDto;
import com.aman.blog.blogrestapi.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPost(int pageNo, int pageSize,String sortBy,String sortDir);
    PostDto getPostById(Long id);
    PostDto updatePostById(PostDto postDto,Long id);
    void deletePostByid(Long id);
}
