package com.ust.FMCG.dto;


import lombok.Data;

@Data
public class UserProfileUpdateRequest {
    private String userId;
    private String empId;
    private String name;
    private String mobile;
    private String newPassword;
}