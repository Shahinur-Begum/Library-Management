package com.example.BookInfo.controller;

import com.example.BookInfo.entity.Report;
import com.example.BookInfo.repository.ReportRepository;
import com.example.BookInfo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportRepository reportRepository;

    // Endpoint to generate or update the report for a student
    @GetMapping("/generate/{studentId}")
    public ResponseEntity<Report> generateReport(@PathVariable Long studentId) {
        Report report = reportService.generateOrUpdateReport(studentId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/allreport")
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }


    // Endpoint to fetch the report for a student
    @GetMapping("/{studentId}")
    public ResponseEntity<Report> getReport(@PathVariable Long studentId) {
        Report report = reportService.getReport(studentId);
        if (report == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(report);
    }

    // In ReportController
    @PutMapping("/clearDues/{studentId}")
    public ResponseEntity<Report> clearDues(@PathVariable Long studentId) {
        reportService.clearDues(studentId);
        return ResponseEntity.ok().build();
    }

}
