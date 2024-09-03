package com.eduunity.controller;

import com.eduunity.GenerateModuleService;
import com.eduunity.request.GenerateModuleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/generate")
public class GenerateModuleController {

    @Autowired
    private GenerateModuleService generateModuleService;

    @PostMapping("/new-module")
    public ResponseEntity<Object> generateNewModule(@RequestBody GenerateModuleRequest generateModuleRequest) {

        Map<String, Object> finalRespObj = new LinkedHashMap<String, Object>();

        String studentId = generateModuleRequest.getStudentId();
        String moduleName = generateModuleRequest.getModuleName();

        if (studentId == null || studentId.isEmpty()) {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "first name is required");
        } else if (moduleName == null || moduleName.isEmpty()) {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "first name is required");
        } else {
            return this.generateModuleService.generateNewModule(generateModuleRequest);
        }

        return new ResponseEntity<Object>(finalRespObj, HttpStatus.OK);
    }

    @GetMapping("/get-modules")
    public ResponseEntity<Object> getAllGeneratedModules(@RequestParam(value = "studentId") String studentId) {

        Map<String, Object> finalRespObj = new LinkedHashMap<String, Object>();

        if (studentId == null || studentId.isEmpty()) {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "Student id is required");
        } else {
            return this.generateModuleService.getAllGeneratedModulesByStudentId(studentId);
        }

        return ResponseEntity.ok(finalRespObj);
    }
}
