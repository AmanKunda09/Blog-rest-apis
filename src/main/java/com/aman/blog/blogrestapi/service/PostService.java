package com.aman.blog.blogrestapi.service;


import com.aman.blog.blogrestapi.payload.PostDto;
import com.aman.blog.blogrestapi.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPost(int pageNo, int pageSize,String sortBy,String sortDir);
    PostDto getPostById(long id);
    PostDto updatePostById(PostDto postDto,long id);
    void deletePostByid(long id);
}
