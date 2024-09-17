package com.eduunity.admin;

import com.eduunity.dto.admin.TrendingSubject;
import com.eduunity.request.admin.TrendingSubjectRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TrandingSubjectService {
    ResponseEntity<Object> saveNewTrandingSubject(TrendingSubjectRequest trendingSubject, MultipartFile imageFile) throws IOException;

    ResponseEntity<Object> getAllTrandingSubjects(int page, int size);

    List<TrendingSubject> trandingSubjectForAppHome();

    ResponseEntity<Object> getNoticeById(int id);

    ResponseEntity<Object> deleteTrandingSubject(int id);
}
