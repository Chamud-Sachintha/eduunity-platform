package com.eduunity.controller;

import com.eduunity.impl.admin.NoticeService;
import com.eduunity.request.admin.NoticeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequestMapping("admin/notice")
@CrossOrigin
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @PostMapping("addNotice")
    public ResponseEntity<Object> saveNewNotice(@RequestBody NoticeRequest noticeRequest) {

        HashMap<String, Object> finalResponse = new HashMap<>();

        String noticeMessage = noticeRequest.getMessage();

        if (noticeMessage == null || noticeMessage == "") {
            finalResponse.put("code", 0);
            finalResponse.put("message", "Empty feild found");
        } else {
            return this.noticeService.addNotice(noticeRequest);
        }

        return ResponseEntity.ok(finalResponse);
    }

    @GetMapping("get-all-notices")
    public ResponseEntity<Object> getAllNotices() {
        return this.noticeService.getAllNotice();
    }

    @PostMapping("update-notice-by-id")
    public ResponseEntity<Object> updateNoticeById(@RequestBody NoticeRequest noticeRequest) {
        HashMap<String, Object> finalResponse = new HashMap<>();

        String noticeMessage = noticeRequest.getMessage();

        if (noticeMessage == null || noticeMessage == "") {
            finalResponse.put("code", 0);
            finalResponse.put("message", "Empty feild found");
        } else {
            return this.noticeService.updateNoticeById(noticeRequest);
        }

        return ResponseEntity.ok(finalResponse);
    }

    @GetMapping("find-notice-by-id")
    public ResponseEntity<Object> findNoticeById(@RequestParam String noticeId) {

        HashMap<String, Object> finalResponse = new HashMap<>();

        if (noticeId == null || noticeId == "") {
            finalResponse.put("code", 0);
            finalResponse.put("message", "Empty feild found");
        } else {
            return this.noticeService.findNoticeById(Integer.parseInt(noticeId));
        }

        return ResponseEntity.ok(finalResponse);
    }

    @PostMapping("delete-notice-by-id")
    public ResponseEntity<Object> deleteNoticeById(@RequestBody NoticeRequest noticeRequest) {
        HashMap<String, Object> finalResponse = new HashMap<>();

        if (noticeRequest.getId() == 0) {
            finalResponse.put("code", 0);
            finalResponse.put("message", "Invalid Request");
        } else {
            return this.noticeService.deleteNoticeById(noticeRequest.getId());
        }

        return ResponseEntity.ok(finalResponse);
    }
}
