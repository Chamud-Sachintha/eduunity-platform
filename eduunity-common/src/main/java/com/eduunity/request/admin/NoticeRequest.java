package com.eduunity.request.admin;

import lombok.Data;

@Data
public class NoticeRequest {
    private int id;
    private String message;
    private int status;
}
