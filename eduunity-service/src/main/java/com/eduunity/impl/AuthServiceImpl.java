package com.eduunity.impl;

import com.eduunity.AuthService;
import com.eduunity.dto.Student;
import com.eduunity.enums.Role;
import com.eduunity.repo.StudentRepository;
import com.eduunity.request.StudentRegisterRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    StudentRepository studentRepository;

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
        studentDetailsEnity.setPassword(studentRegisterRequest.getPassword());
        studentDetailsEnity.setRole(Role.STUDENT);

        Student newStudent = this.studentRepository.save(studentDetailsEnity);

        finalRespObj.put("code", 1);
        finalRespObj.put("message", "student created successfully");
        finalRespObj.put("data", newStudent);

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
