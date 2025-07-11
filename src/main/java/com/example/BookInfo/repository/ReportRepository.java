package com.example.BookInfo.repository;

import com.example.BookInfo.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Report findByStudentId(Long studentId);
}
