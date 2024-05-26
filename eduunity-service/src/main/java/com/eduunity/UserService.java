package com.eduunity;

import org.springframework.http.ResponseEntity;

public interface UserService {

    public ResponseEntity<Object> getUserDetails(int uid);
}
