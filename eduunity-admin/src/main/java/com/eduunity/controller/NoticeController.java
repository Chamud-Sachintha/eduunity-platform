package com.eduunity.controller;

import com.eduunity.impl.admin.NoticeService;
import com.eduunity.request.admin.NoticeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    public ResponseEntity<Object> saveNewNotice(@RequestBody NoticeRequest noticeRequest) {
        return null;
    }
}
