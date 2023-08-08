package com.HelloSpring.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponce
{
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
