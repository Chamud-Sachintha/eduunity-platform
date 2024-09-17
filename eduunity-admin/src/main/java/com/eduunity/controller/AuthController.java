package com.eduunity.controller;

import com.eduunity.AuthService;
import com.eduunity.request.admin.AdminAuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("admin/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> authenticateAdmin(@RequestBody AdminAuthRequest adminAuthRequest) {
        Map<String, Object> finalRespObj = new LinkedHashMap<String, Object>();

        String username = adminAuthRequest.getUsername();
        String password = adminAuthRequest.getPassword();

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "all fields are required");

            return new ResponseEntity<Object>(finalRespObj, HttpStatus.OK);
        }

        return this.authService.authenticateAdminUser(adminAuthRequest);
    }
}
