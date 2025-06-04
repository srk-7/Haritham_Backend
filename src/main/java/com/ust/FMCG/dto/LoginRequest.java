package com.ust.FMCG.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String mobile;
    private String password;
}