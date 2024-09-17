package com.eduunity.impl.admin;

import com.eduunity.admin.StudentService;
import com.eduunity.dto.Student;
import com.eduunity.repo.StudentRepository;
import com.eduunity.request.admin.StudentUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

        for (Student student : studentList.getContent()) {
            student.setModules(null);
        }

        response.put("data", studentList);
        finalRespObj.put("code", 1);
        finalRespObj.put("message", "Operation Success");
        finalRespObj.put("data", response);

        return ResponseEntity.ok(finalRespObj);
    }

    @Override
    public ResponseEntity<Object> updateStudentById(StudentUpdateRequest studentUpdateRequest) {
        HashMap<String, Object> finalRespObj = new HashMap<>();
        Map<String, Object> response = new HashMap<>();

        Optional<Student> student = this.validateStudent(studentUpdateRequest.getStudentId());

        if (student != null) {
            student.get().setFirstName(studentUpdateRequest.getFirstName());
            student.get().setLastName(studentUpdateRequest.getLastName());
            student.get().setEmail(studentUpdateRequest.getEmail());
            student.get().setStatus(studentUpdateRequest.getStatus());
            student.get().setNicNumber(studentUpdateRequest.getNicNumber());

            Student updatedStudent = this.studentRepository.save(student.get());

            response.put("data", updatedStudent);
            finalRespObj.put("code", 1);
            finalRespObj.put("message", "Student Updated Successfully");
            finalRespObj.put("data", response);
        } else {
            response.put("data", "");
            finalRespObj.put("code", 1);
            finalRespObj.put("message", "Student Not Found");
            finalRespObj.put("data", response);
        }

        return ResponseEntity.ok(finalRespObj);
    }

    private Optional<Student> validateStudent(int studentId) {
        Optional<Student> student = this.studentRepository.findById(studentId);

        if (student.isEmpty()) {
            return null;
        }

        return student;
    }
}
