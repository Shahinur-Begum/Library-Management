package com.example.BookInfo.service;

import com.example.BookInfo.dto.LoginDto;
import com.example.BookInfo.entity.Admin;
import com.example.BookInfo.entity.Student;
import com.example.BookInfo.exception.InvalidCredentialsException;
import com.example.BookInfo.repository.AdminRepository;
import com.example.BookInfo.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StudentRepository studentRepository;

    // Admin Login
    public String login(LoginDto loginDto) {
        Admin admin = adminRepository.findByEmail(loginDto.getEmail());
        if (admin == null || !BCrypt.checkpw(loginDto.getPassword(), admin.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password!");
        }
        return "Admin logged in successfully!";
    }

    // Fetch Admin by Email
    public Admin getAdminByEmail(String email) {
        Admin admin = adminRepository.findByEmail(email);
        if (admin == null) {
            throw new EntityNotFoundException("Admin with email " + email + " not found!");
        }
        return admin;
    }

    // Add Student
    public String addStudent(Student student) {
        validateStudent(student);
        studentRepository.save(student);
        return "Student added successfully!";
    }

    // Update Student
    public String updateStudent(Long id, Student student) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if (!existingStudent.isPresent()) {
            throw new EntityNotFoundException("Student with ID " + id + " not found!");
        }
        Student s = existingStudent.get();
        s.setId(student.getId());
        s.setName(student.getName());
        s.setEmail(student.getEmail());
        s.setDept(student.getDept());
        s.setBatch(student.getBatch());
        s.setInterest(student.getInterest());
        studentRepository.save(s);
        return "Student updated successfully!";
    }

    // Delete Student
    public String deleteStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            throw new EntityNotFoundException("Student with ID " + id + " not found!");
        }
        studentRepository.deleteById(id);
        return "Student deleted successfully!";
    }

    // Get All Students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Search Student by ID
    public Student searchStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            throw new EntityNotFoundException("Student with ID " + id + " not found!");
        }
        return student.get();
    }

    // Validate Student
    private void validateStudent(Student student) {
        if (student.getName() == null || student.getEmail() == null || student.getId() == null) {
            throw new IllegalArgumentException("Student name, email and ID cannot be null!");
        }
    }
}