package com.eduunity.impl;

import com.eduunity.AuthService;
import com.eduunity.JwtService;
import com.eduunity.dto.Student;
import com.eduunity.enums.Role;
import com.eduunity.repo.AdminRepository;
import com.eduunity.repo.StudentRepository;
import com.eduunity.request.StudentAuthRequest;
import com.eduunity.request.StudentRegisterRequest;
import com.eduunity.request.admin.AdminAuthRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public ResponseEntity<Object> registerNewStudent(StudentRegisterRequest studentRegisterRequest) {

        Map<String, Object> finalRespObj = new LinkedHashMap<String, Object>();

        Student studentDetailsEnity = new Student();

        if (this.validateStudentNIC(studentRegisterRequest.getNicNumber())) {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "student is already exist");

            return new ResponseEntity<Object>(finalRespObj, HttpStatus.OK);
        }

        studentDetailsEnity.setFirstName(studentRegisterRequest.getFirstName());
        studentDetailsEnity.setLastName(studentRegisterRequest.getLastName());
        studentDetailsEnity.setNicNumber(studentRegisterRequest.getNicNumber());
        studentDetailsEnity.setEmail(studentRegisterRequest.getEmail());
        studentDetailsEnity.setPassword(passwordEncoder.encode(studentRegisterRequest.getPassword()));
        studentDetailsEnity.setRole(Role.STUDENT);
        studentDetailsEnity.setStatus(1);

        Student newStudent = this.studentRepository.save(studentDetailsEnity);

        finalRespObj.put("code", 1);
        finalRespObj.put("message", "student created successfully");
        finalRespObj.put("data", newStudent);

        return new ResponseEntity<Object>(finalRespObj, HttpStatus.OK);
    }

    public ResponseEntity<Object> authenticateStudent(StudentAuthRequest studentAuthRequest) {

        Map<String, Object> finalRespObj = new LinkedHashMap<String, Object>();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        studentAuthRequest.getUserName(),
                        studentAuthRequest.getPassword()
                )
        );

         var student = studentRepository.findByEmail(studentAuthRequest.getUserName())
                 .orElseThrow();

         var jwtToken = jwtService.generateToken(student);

         finalRespObj.put("code", 1);
         finalRespObj.put("data", student);
         finalRespObj.put("token", jwtToken);
         finalRespObj.put("message", "student authenticated successfully");

         return new ResponseEntity<Object>(finalRespObj, HttpStatus.OK);
    }

    public ResponseEntity<Object> authenticateAdminUser(AdminAuthRequest adminAuthRequest) {
        Map<String, Object> finalRespObj = new LinkedHashMap<String, Object>();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        adminAuthRequest.getUsername(),
                        adminAuthRequest.getPassword()
                )
        );

        var adminUser = adminRepository.findByUserName(adminAuthRequest.getUsername())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(adminUser);

        finalRespObj.put("code", 1);
        finalRespObj.put("data", adminUser);
        finalRespObj.put("token", jwtToken);
        finalRespObj.put("message", "student authenticated successfully");

        return new ResponseEntity<Object>(finalRespObj, HttpStatus.OK);
    }

    private boolean validateStudentNIC(String nicNumber) {
        Optional<Student> studentDetails = this.studentRepository.findByNicNumber(nicNumber);

        if (studentDetails.isPresent()) {
            return true;
        }

        return false;
    }

    @Override
    public String sayHellow() {
        return "hellort";
    }
}
