package com.example.BookInfo.controller;

import com.example.BookInfo.dto.LoginDto;
import com.example.BookInfo.dto.SignupDto;
import com.example.BookInfo.entity.Student;
import com.example.BookInfo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/student")
public class StudentController1 {

    @Autowired
    private StudentService studentService;

    // Student Signup
    @PostMapping("/signup")
    public ResponseEntity<String> studentSignup(@RequestBody SignupDto signupDto) {
        try {
            String response = studentService.signup(signupDto);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Student Login
    @PostMapping("/login")
    public ResponseEntity<Student> studentLogin(@RequestBody LoginDto loginDto) {
        try {
            Student student = studentService.login(loginDto);
            return ResponseEntity.ok(student);  // Respond with student info
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();  // 401
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<Student> getStudentProfile(@RequestParam String email) {
        try {
            // Fetch the student using the email
            Student student = studentService.getStudentByEmail(email);
            return ResponseEntity.ok(student);
        } catch (IllegalArgumentException e) {
            // Handle case when student is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
