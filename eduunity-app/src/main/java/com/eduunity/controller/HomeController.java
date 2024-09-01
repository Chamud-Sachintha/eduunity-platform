package com.eduunity.controller;

import com.eduunity.UserService;
import com.eduunity.admin.TrandingSubjectService;
import com.eduunity.dto.admin.TrendingSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("home")
public class HomeController {

    @Autowired
    private TrandingSubjectService trandingSubjectService;

    @GetMapping("/index")
    public ResponseEntity<Object> homeIndex() {

        HashMap<String, Object> finalResponse = new HashMap<>();
        HashMap<String, Object> response = new HashMap<>();

        List<TrendingSubject> trendingSubjectList = this.trandingSubjectService.trandingSubjectForAppHome();
        response.put("trendingSubjectList", trendingSubjectList);

        finalResponse.put("code", 1);
        finalResponse.put("message", "success");
        finalResponse.put("data", response);

        return new ResponseEntity<>(finalResponse, HttpStatus.OK);
    }
}
