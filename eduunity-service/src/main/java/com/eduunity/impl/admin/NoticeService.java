package com.eduunity.impl.admin;

import com.eduunity.dto.admin.Notice;
import com.eduunity.repo.NoticeRepository;
import com.eduunity.request.admin.NoticeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    public ResponseEntity<Object> addNotice(NoticeRequest noticeRequest) {
        return null;
    }
}
