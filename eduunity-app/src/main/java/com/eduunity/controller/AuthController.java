package com.eduunity.controller;

import com.eduunity.AuthService;
import com.eduunity.request.StudentAuthRequest;
import com.eduunity.request.StudentRegisterRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerNewStudent(@RequestBody StudentRegisterRequest studentDetails) {

        Map<String, Object> finalRespObj = new LinkedHashMap<String, Object>();

        String firstName = studentDetails.getFirstName();
        String lastName = studentDetails.getLastName();
        String nicNumber = studentDetails.getNicNumber();
        String email = studentDetails.getEmail();
        String password = studentDetails.getPassword();

        if (firstName == null || firstName.isEmpty()) {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "first name is required");

            return new ResponseEntity<Object>(finalRespObj, HttpStatus.OK);
        }

        if (lastName == null || lastName.isEmpty()) {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "last name is required");

            return new ResponseEntity<Object>(finalRespObj, HttpStatus.OK);
        }

        if (nicNumber == null || nicNumber.isEmpty()) {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "nic number is required");

            return new ResponseEntity<Object>(finalRespObj, HttpStatus.OK);
        }

        if (email == null || email.isEmpty()) {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "email is required");

            return new ResponseEntity<Object>(finalRespObj, HttpStatus.OK);
        }

        if (password == null || password.isEmpty()) {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "password is required");

            return new ResponseEntity<Object>(finalRespObj, HttpStatus.OK);
        }

        return this.authService.registerNewStudent(studentDetails);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> studentAuthenticate(@RequestBody StudentAuthRequest studentAuthRequest) {
        Map<String, Object> finalRespObj = new LinkedHashMap<String, Object>();

        String userName = studentAuthRequest.getUserName();
        String password = studentAuthRequest.getPassword();

        if (userName == null || userName.isEmpty()) {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "user name is required");

            return new ResponseEntity<Object>(finalRespObj, HttpStatus.OK);
        }

        if (password == null || password.isEmpty()) {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "password is required");

            return new ResponseEntity<Object>(finalRespObj, HttpStatus.OK);
        }

        return this.authService.authenticateStudent(studentAuthRequest);

    }

    @GetMapping("/test")
    public String sayHellow() {
        return authService.sayHellow();
    }
}
