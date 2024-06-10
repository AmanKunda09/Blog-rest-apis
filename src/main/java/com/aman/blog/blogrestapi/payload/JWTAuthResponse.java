package com.aman.blog.blogrestapi.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JWTAuthResponse {
    private String accessToken;
    private String tokenType="Bearer";
}
