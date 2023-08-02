package com.aman.blog.blogrestapi.repository;

import com.aman.blog.blogrestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

}
