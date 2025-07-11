package com.example.BookInfo.repository;

import com.example.BookInfo.entity.Admin;
import com.example.BookInfo.entity.Feedback;
import com.example.BookInfo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByStudentAndAdmin(Student student, Admin admin);
    // Custom queries can be added here if needed
}
