package com.eduunity.controller;

import com.eduunity.admin.DashboardStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/home")
public class DashboardStatsController {

    @Autowired
    private DashboardStatisticsService dashboardStatisticsService;

    @GetMapping("/dashboard")
    public ResponseEntity<Object> getDashboardStats() {
        return (this.dashboardStatisticsService.getDashboardStatistics());
    }
}
