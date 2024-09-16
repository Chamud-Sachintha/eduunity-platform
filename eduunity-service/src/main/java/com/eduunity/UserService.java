package com.eduunity;

import com.eduunity.dto.Student;

import java.util.Optional;

public interface UserService {

    public Optional<Student> getUserDetails(int uid);
}
