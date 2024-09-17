package com.eduunity.controller;

import com.eduunity.admin.DashboardStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin/home")
@CrossOrigin
public class DashboardStatsController {

    @Autowired
    private DashboardStatisticsService dashboardStatisticsService;

    @GetMapping("/dashboard")
    public ResponseEntity<Object> getDashboardStats() {
        return (this.dashboardStatisticsService.getDashboardStatistics());
    }

    @GetMapping("/activeUserList")
    public ResponseEntity<Object> getActiveStudentList(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
        return (this.dashboardStatisticsService.getActiveStudentList(pageNo, pageSize));
    }
}
