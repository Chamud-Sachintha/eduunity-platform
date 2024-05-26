package com.eduunity.impl.admin;

import com.eduunity.admin.StudentService;
import com.eduunity.dto.Student;
import com.eduunity.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public ResponseEntity<Object> getAllStudentList(int page, int size) {
        HashMap<String, Object> finalRespObj = new HashMap<>();
        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of(page, size);

        Page<Student> studentList = this.studentRepository.findAll(pageable);

        response.put("data", studentList);
        finalRespObj.put("code", 1);
        finalRespObj.put("message", "Operation Success");
        finalRespObj.put("data", response);

        return ResponseEntity.ok(finalRespObj);
    }
}
