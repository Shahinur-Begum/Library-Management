package com.example.BookInfo.controller;


import com.example.BookInfo.entity.Feedback;
import com.example.BookInfo.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // Endpoint to submit feedback
    @PostMapping("/submit/{studentId}/{adminId}")
    public Feedback submitFeedback(
            @PathVariable Long studentId,
            @PathVariable Long adminId,
            @RequestBody Feedback feedback) {
        return feedbackService.saveFeedback(studentId, adminId, feedback);
    }

    // Endpoint to get feedback for a specific admin
    @GetMapping("/all")
    public List<Feedback> getAllFeedback() {
        return feedbackService.getAllFeedback();
    }

    //Endpoint to delete feedback by ID
    @DeleteMapping("/delete/{feedbackId}")
    public String deleteFeedback(@PathVariable Long feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return "Feedback with ID " + feedbackId + " has been deleted successfully.";
    }
}