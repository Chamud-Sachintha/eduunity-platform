package com.eduunity.controller;

import com.eduunity.admin.StudentService;
import com.eduunity.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin/student")
public class StudentController {

    @Autowired
    private StudentService  studentService;

    @GetMapping("/list")
    public ResponseEntity<Object> getAllStudentList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return (this.studentService.getAllStudentList(page, size));
    }
}
