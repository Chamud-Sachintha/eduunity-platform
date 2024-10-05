package com.eduunity.impl;

import com.eduunity.UserService;
import com.eduunity.dto.Student;
import com.eduunity.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private StudentRepository studentRepository;


    @Override
    public Optional<Student> getUserDetails(int uid) {
        Optional<Student> studentInfo = this.studentRepository.findById(uid);
        studentInfo.get().setModules(null);

        return studentInfo;
    }
}
