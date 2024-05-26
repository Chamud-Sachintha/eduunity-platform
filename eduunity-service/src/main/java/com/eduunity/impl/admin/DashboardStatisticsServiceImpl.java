package com.eduunity.impl.admin;

import com.eduunity.admin.DashboardStatisticsService;
import com.eduunity.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

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

    private int getStudentsCountByStatus(int studentStatus) {
        return  (this.studentRepository.countStudentsByStatus(studentStatus));
    }
}
