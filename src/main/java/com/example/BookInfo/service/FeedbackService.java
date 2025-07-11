package com.example.BookInfo.service;

import com.example.BookInfo.entity.Admin;

import com.example.BookInfo.entity.Feedback;

import com.example.BookInfo.entity.Student;

import com.example.BookInfo.repository.AdminRepository;

import com.example.BookInfo.repository.FeedbackRepository;

import com.example.BookInfo.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



import java.util.List;



@Service

public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdminRepository adminRepository;

    // Method to save feedback

    public Feedback saveFeedback(Long studentId, Long adminId, Feedback feedback) {

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));

        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new RuntimeException("Admin not found"));

        feedback.setStudent(student);

        feedback.setAdmin(admin);

        return feedbackRepository.save(feedback);

    }

    // Method to get all feedback for a specific admin

    public List<Feedback> getAllFeedback() {

        return feedbackRepository.findAll(); // Fetch all feedback

    }

    public void deleteFeedback(Long feedbackId) {
        if (!feedbackRepository.existsById(feedbackId)) {
            throw new RuntimeException("Feedback not found");
        }
        feedbackRepository.deleteById(feedbackId);
    }

}