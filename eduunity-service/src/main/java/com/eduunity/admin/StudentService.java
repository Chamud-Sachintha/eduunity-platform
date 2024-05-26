package com.eduunity.admin;

import org.springframework.http.ResponseEntity;

public interface StudentService {

    public ResponseEntity<Object> getAllStudentList(int page, int size);
}
