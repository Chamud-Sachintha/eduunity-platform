package com.eduunity.controller;

import com.eduunity.admin.TrandingSubjectService;
import com.eduunity.request.admin.TrendingSubjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("admin/trending")
@CrossOrigin
public class TrendingSubjectController {

    @Autowired
    private TrandingSubjectService trandingSubjectService;

    @PostMapping("/addTrendingSubject")
    public ResponseEntity<Object> saveNewTrandingSubject(@ModelAttribute TrendingSubjectRequest trendingSubjectRequest,@RequestParam("file") MultipartFile multipartFile) throws IOException {

        HashMap<String, Object> finalRespObj = new HashMap<>();

        String subjectName = trendingSubjectRequest.getSubjectName();
        String subjectDescription = trendingSubjectRequest.getSubjectDescription();

        if (subjectName.isEmpty() || subjectDescription.isEmpty()) {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "All Fields Are Required");
            finalRespObj.put("data", null);
        } else {
            return this.trandingSubjectService.saveNewTrandingSubject(trendingSubjectRequest, multipartFile);
        }

        return ResponseEntity.ok(finalRespObj);
    }

    @GetMapping("/getAllTrendingSubList")
    public ResponseEntity<Object> getAllTrendingSubjectList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return (this.trandingSubjectService.getAllTrandingSubjects(page, size));
    }

    @PostMapping("get-subject-by-id")
    public ResponseEntity<Object> getSubjectById(@RequestParam("id") String subjectId) {

        HashMap<String, Object> finalRespObj = new HashMap<>();

        if (subjectId.isEmpty()) {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "All Fields Are Required");
        } else {
            return this.trandingSubjectService.getNoticeById(Integer.parseInt(subjectId));
        }

        return ResponseEntity.ok(finalRespObj);
    }

    @PostMapping("delete-subject-by-id")
    public ResponseEntity<Object> deleteSubjectById(@RequestParam("id") String subjectId) {

        HashMap<String, Object> finalRespObj = new HashMap<>();

        if (subjectId.isEmpty() || subjectId == null) {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "Allfeilds are required");
        } else {
            return this.trandingSubjectService.deleteTrandingSubject(Integer.parseInt(subjectId));
        }

        return ResponseEntity.ok(finalRespObj);
    }
}
