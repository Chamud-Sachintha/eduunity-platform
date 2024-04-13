package com.eduunity.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentRegisterRequest {

    private String firstName;
    private String lastName;
    private String nicNumber;
    private String email;
    private String password;
}
