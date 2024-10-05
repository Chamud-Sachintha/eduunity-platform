package com.eduunity.impl.admin;

import com.eduunity.dto.admin.Notice;
import com.eduunity.repo.NoticeRepository;
import com.eduunity.request.admin.NoticeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    public ResponseEntity<Object> addNotice(NoticeRequest noticeRequest) {

        HashMap<String, Object> finalRespObj = new HashMap<>();
        HashMap<String, Object> response = new HashMap<>();
        Notice notice = new Notice();

        notice.setMessage(noticeRequest.getMessage());
        notice.setStatus(noticeRequest.getStatus());

        Notice savedNoticeInfo = this.noticeRepository.save(notice);

        response.put("data", savedNoticeInfo);
        finalRespObj.put("code", 1);
        finalRespObj.put("message", "Operation Success");
        finalRespObj.put("data", response);

        return ResponseEntity.ok(finalRespObj);
    }

    public ResponseEntity<Object> getAllNotice() {

        HashMap<String, Object> finalRespObj = new HashMap<>();
        List<Notice> noticeListTable = new ArrayList<>();

        List<Notice> noticeList = this.noticeRepository.findAll();
        for (Notice notice : noticeList) {
            Notice noticeInfo = new Notice();

            noticeInfo.setId(notice.getId());
            noticeInfo.setMessage(notice.getMessage());
            noticeInfo.setStatus(notice.getStatus());

            noticeListTable.add(noticeInfo);
        }

        finalRespObj.put("code", 1);
        finalRespObj.put("message", "Operation Success");
        finalRespObj.put("data", noticeListTable);

        return ResponseEntity.ok(finalRespObj);
    }

    public ResponseEntity<Object> updateNoticeById(NoticeRequest noticeRequest) {

        HashMap<String, Object> response = new HashMap<>();
        Optional<Notice> getNotice = this.noticeRepository.findById(noticeRequest.getId());

        if (getNotice.isPresent()) {
            getNotice.get().setMessage(noticeRequest.getMessage());
            getNotice.get().setStatus(noticeRequest.getStatus());

            return ResponseEntity.ok(noticeRepository.save(getNotice.get()));
        } else {
            response.put("code", 0);
            response.put("message", "Not found");
        }

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Object> findNoticeById(int noticeId) {

        HashMap<String, Object> response = new HashMap<>();
        Optional<Notice> getNotice = this.noticeRepository.findById(noticeId);

        if (getNotice.isPresent()) {
            return ResponseEntity.ok(noticeRepository.save(getNotice.get()));
        } else {
            response.put("code", 0);
            response.put("message", "Not found");
        }

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Object> deleteNoticeById(int noticeId) {

        HashMap<String, Object> response = new HashMap<>();

        try {
            this.noticeRepository.deleteById(noticeId);

            response.put("code", 1);
            response.put("message", "Deleted");
        } catch (Exception e) {
            response.put("code", 0);
            response.put("message", "Error Occured");
        }

        return ResponseEntity.ok(response);
    }

    public List<Notice> getNoticeListForAppHome() {
        List<Notice> noticeList = new ArrayList<>();

        noticeList = this.noticeRepository.findAll();
        return noticeList;
    }
}
