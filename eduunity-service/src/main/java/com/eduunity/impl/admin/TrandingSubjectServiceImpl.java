package com.eduunity.impl.admin;

import com.eduunity.admin.TrandingSubjectService;
import com.eduunity.dto.admin.TrendingSubject;
import com.eduunity.impl.ImageService;
import com.eduunity.repo.TrandingSubjectRepository;
import com.eduunity.request.admin.TrendingSubjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class TrandingSubjectServiceImpl implements TrandingSubjectService {

    private static final String UPLOAD_PATH = "src/main/resources/static/images/trending_subjects";

    @Autowired
    private TrandingSubjectRepository trandingSubjectRepository;

    @Autowired
    private ImageService imageService;

    @Override
    public ResponseEntity<Object> saveNewTrandingSubject(TrendingSubjectRequest trendingSubject, MultipartFile imageFile) throws IOException {

        HashMap<String, Object> finalRespObj = new HashMap<>();
        HashMap<String, Object> response = new HashMap<>();

        TrendingSubject saveTrendingSubject = new TrendingSubject();
        saveTrendingSubject.setSubjectName(trendingSubject.getSubjectName());
        saveTrendingSubject.setSubjectDescription(trendingSubject.getSubjectDescription());

        String imageFileString = this.imageService.saveImageToLocalStorage(UPLOAD_PATH, imageFile);

        saveTrendingSubject.setSubjectImage(imageFileString);

        TrendingSubject savedTrendingSubjectObj = this.trandingSubjectRepository.save(saveTrendingSubject);

        response.put("data", savedTrendingSubjectObj);
        finalRespObj.put("code", 1);
        finalRespObj.put("message", "Operation Success");
        finalRespObj.put("data", response);

        return new ResponseEntity<>(finalRespObj, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAllTrandingSubjects(int page, int size) {
        HashMap<String, Object> finalRespObj = new HashMap<>();
        HashMap<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of(page, size);

        Page<TrendingSubject> allTrendingSubList = this.trandingSubjectRepository.findAll(pageable);

        response.put("data", allTrendingSubList);
        finalRespObj.put("code", 1);
        finalRespObj.put("message", "Operation Success");
        finalRespObj.put("data", response);

        return new ResponseEntity<>(finalRespObj, HttpStatus.OK);
    }

    @Override
    public List<TrendingSubject> trandingSubjectForAppHome() {
        List<TrendingSubject> trendingSubjectOptional = this.trandingSubjectRepository.findAll();
        return trendingSubjectOptional;
    }
}
