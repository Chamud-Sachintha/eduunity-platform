package com.eduunity;

import com.eduunity.request.StudentAuthRequest;
import com.eduunity.request.StudentRegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    public ResponseEntity<Object> registerNewStudent(StudentRegisterRequest studentRegisterRequest);

    public ResponseEntity<Object> authenticateStudent(StudentAuthRequest studentAuthRequest);

    public String sayHellow();
}
