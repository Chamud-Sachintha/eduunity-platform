package com.eduunity.admin;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface DashboardStatisticsService {

    public ResponseEntity<Object> getDashboardStatistics();

    public ResponseEntity<Object> getActiveStudentList(int page, int size);
}
