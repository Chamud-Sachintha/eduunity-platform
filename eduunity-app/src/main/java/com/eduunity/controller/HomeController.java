package com.eduunity.controller;

import com.eduunity.UserService;
import com.eduunity.admin.TrandingSubjectService;
import com.eduunity.dto.Student;
import com.eduunity.dto.admin.TrendingSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("home")
@CrossOrigin
public class HomeController {

    @Autowired
    private TrandingSubjectService trandingSubjectService;

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public ResponseEntity<Object> homeIndex(@RequestParam String userId) {

        HashMap<String, Object> finalResponse = new HashMap<>();
        HashMap<String, Object> response = new HashMap<>();

        if (userId == null || userId.isEmpty()) {
            finalResponse.put("status", 0);
            finalResponse.put("message", "Please enter a valid user ID");
        } else {
            List<TrendingSubject> trendingSubjectList = this.trandingSubjectService.trandingSubjectForAppHome();
            Optional<Student> student = this.userService.getUserDetails(Integer.parseInt(userId));
            response.put("trendingSubjectList", trendingSubjectList);
            response.put("student", student);

            finalResponse.put("code", 1);
            finalResponse.put("message", "success");
            finalResponse.put("data", response);
        }

        return new ResponseEntity<>(finalResponse, HttpStatus.OK);
    }
}
