package com.eduunity;

import com.eduunity.request.StudentRegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    public ResponseEntity<Object> registerNewStudent(StudentRegisterRequest studentRegisterRequest);
    public String sayHellow();
}
