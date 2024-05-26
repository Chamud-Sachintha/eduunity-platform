package com.eduunity;

import com.eduunity.dto.admin.AdminUser;
import com.eduunity.enums.Role;
import com.eduunity.repo.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AdminAPIApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminAPIApplication.class, args);
    }

}
