package com.eduunity.controller;

import com.eduunity.GenerateModuleService;
import com.eduunity.request.GenerateModuleContentRequest;
import com.eduunity.request.GenerateModuleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/generate")
@CrossOrigin
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

    @PostMapping("/get-module-content")
    public  ResponseEntity<Object> generateModuleContent(@RequestBody GenerateModuleContentRequest moduleContentRequest) {

        Map<String, Object> finalRespObj = new LinkedHashMap<String, Object>();

        String moduleId = moduleContentRequest.getModuleId();
        String moduleContentName = moduleContentRequest.getModuleContentName();

        if (moduleId == null || moduleId.isEmpty()) {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "Module ID is required");
        } else if (moduleContentName == null || moduleContentName.isEmpty()) {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "Module content name is required");
        } else {
            return this.generateModuleService.generateModuleContent(Integer.parseInt(moduleId), moduleContentName);
        }

        return ResponseEntity.ok(finalRespObj);
    }

    @GetMapping("get-topics")
    public ResponseEntity<Object> getTopicsByModuleId(@RequestParam(value = "moduleId") String moduleId) {

        HashMap<String, Object> finalRespObj = new LinkedHashMap<String, Object>();

        if (moduleId == null || moduleId.isEmpty()) {
            finalRespObj.put("code", 0);
            finalRespObj.put("message", "Module ID is required");
        } else {
            return this.generateModuleService.getAllTopicsByModuleId(moduleId);
        }

        return ResponseEntity.ok(finalRespObj);
    }

    @PostMapping("level-up-module")
    public ResponseEntity<Object> levelUpModule(@RequestBody GenerateModuleRequest generateModuleRequest) {

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
            generateModuleRequest.setExperiancedLevel(generateModuleRequest.getExperiancedLevel() + 1);

            if (generateModuleRequest.getExperiancedLevel() > 3) {
                finalRespObj.put("code", 0);
                finalRespObj.put("message", "Invalid Experiance level");
            } else {
                return this.generateModuleService.generateNewModule(generateModuleRequest);
            }
        }

        return new ResponseEntity<Object>(finalRespObj, HttpStatus.OK);
    }
}
