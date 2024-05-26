package com.eduunity.impl.admin;

import com.eduunity.admin.DashboardStatisticsService;
import com.eduunity.dto.Student;
import com.eduunity.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DashboardStatisticsServiceImpl implements DashboardStatisticsService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public ResponseEntity<Object> getDashboardStatistics() {

        Map<String, Object> finalRespObj = new LinkedHashMap<String, Object>();
        Map<String, Object> response = new LinkedHashMap<String, Object>();

        int activeStudentCount = getStudentsCountByStatus(1);
        int inactiveStudentCount = getStudentsCountByStatus(0);

        response.put("activeStudentCount", activeStudentCount);
        response.put("inactiveStudentCount", inactiveStudentCount);

        finalRespObj.put("code", 1);
        finalRespObj.put("message", "Operation Success");
        finalRespObj.put("data", response);

        return new ResponseEntity<Object>(finalRespObj, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getActiveStudentList(int page, int size) {

        Map<String, Object> finalRespObj = new LinkedHashMap<String, Object>();
        Map<String, Object> response = new LinkedHashMap<String, Object>();

        Pageable pageable = PageRequest.of(page, size);

        Page<Student> studentList = this.studentRepository.findStudentsByStatus(1, pageable);

        response.put("data", studentList);
        finalRespObj.put("code", 1);
        finalRespObj.put("message", "Operation Success");
        finalRespObj.put("data", response);

        return new ResponseEntity<Object>(finalRespObj, HttpStatus.OK);
    }

    private int getStudentsCountByStatus(int studentStatus) {
        return  (this.studentRepository.countStudentsByStatus(studentStatus));
    }
}
