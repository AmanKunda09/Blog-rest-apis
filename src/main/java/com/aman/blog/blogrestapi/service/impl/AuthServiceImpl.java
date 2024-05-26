package com.aman.blog.blogrestapi.service.impl;

import com.aman.blog.blogrestapi.entity.Role;
import com.aman.blog.blogrestapi.entity.User;
import com.aman.blog.blogrestapi.exceptions.BlogAPIException;
import com.aman.blog.blogrestapi.payload.LoginDto;
import com.aman.blog.blogrestapi.payload.RegisterDto;
import com.aman.blog.blogrestapi.repository.RoleRepository;
import com.aman.blog.blogrestapi.repository.UserRepository;
import com.aman.blog.blogrestapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User logged in successfully";
    }

    @Override
    public String register(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername()))
        {
            throw new BlogAPIException(HttpStatus.NOT_FOUND,"no user exists by this username");
        }
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new BlogAPIException(HttpStatus.NOT_FOUND,"no user exits by the email");
        }
        User user=new User();
        user.setEmail(registerDto.getEmail());
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Set<Role> role=new HashSet<>();
        Role userRole=roleRepository.findByName("ROLE_USER").get();
        role.add(userRole);
        user.setRoles(role);
        userRepository.save(user);
        return "user registered successfully";
    }
}
