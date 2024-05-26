package com.eduunity.request.admin;

import lombok.Data;

@Data
public class AdminAuthRequest {
    private String username;
    private String password;
}
