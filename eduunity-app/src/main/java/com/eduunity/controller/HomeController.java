package com.eduunity.controller;

import com.eduunity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("home")
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String homeIndex() {
        return null;
    }
}
