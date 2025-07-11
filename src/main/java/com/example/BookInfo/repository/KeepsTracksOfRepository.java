package com.example.BookInfo.repository;

import com.example.BookInfo.entity.KeepsTracksOf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeepsTracksOfRepository extends JpaRepository<KeepsTracksOf, Long> {
    // Custom query to find all records for a specific student
    List<KeepsTracksOf> findByStudentId(Long studentId);

    // Custom query to find all records for a specific admin
    List<KeepsTracksOf> findByAdminId(Long adminId);
}
