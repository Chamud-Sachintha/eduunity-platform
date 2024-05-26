package com.eduunity.impl;

import com.eduunity.UserService;
import com.eduunity.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private StudentRepository studentRepository;


    @Override
    public ResponseEntity<Object> getUserDetails(int uid) {
        return null;
    }
}
