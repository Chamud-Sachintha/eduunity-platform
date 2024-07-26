package com.eduunity.request.admin;

import lombok.Data;

@Data
public class StudentUpdateRequest {
    private int studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String nicNumber;
    private int status;
}
