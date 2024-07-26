package com.eduunity.controller;

import com.eduunity.admin.StudentService;
import com.eduunity.repo.StudentRepository;
import com.eduunity.request.admin.StudentUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequestMapping("admin/student")
public class StudentController {

    @Autowired
    private StudentService  studentService;

    @GetMapping("/list")
    public ResponseEntity<Object> getAllStudentList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return (this.studentService.getAllStudentList(page, size));
    }

    @PostMapping("/updateStudent")
    public ResponseEntity<Object> updateStudent(@RequestBody StudentUpdateRequest studentUpdateRequest) {

        HashMap<String, Object> finalRespObj = new HashMap<>();

        String firstName = studentUpdateRequest.getFirstName();
        String lastName = studentUpdateRequest.getLastName();
        String email = studentUpdateRequest.getEmail();
        String nicNumber = studentUpdateRequest.getNicNumber();
        String status = String.valueOf(studentUpdateRequest.getStatus());

        if (firstName != null && lastName != null && email != null && nicNumber != null && status != null) {
            return (this.studentService.updateStudentById(studentUpdateRequest));
        } else {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "All Fields Are Required");
            finalRespObj.put("data", null);
        }

        return ResponseEntity.ok(finalRespObj);
    }
}
