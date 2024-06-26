package com.aman.blog.blogrestapi.service;

import com.aman.blog.blogrestapi.payload.LoginDto;
import com.aman.blog.blogrestapi.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
