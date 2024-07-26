package com.eduunity.admin;

import com.eduunity.request.admin.StudentUpdateRequest;
import org.springframework.http.ResponseEntity;

public interface StudentService {

    public ResponseEntity<Object> getAllStudentList(int page, int size);

    public ResponseEntity<Object> updateStudentById(StudentUpdateRequest studentUpdateRequest);
}
