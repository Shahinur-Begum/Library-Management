package com.example.BookInfo.service;



import com.example.BookInfo.dto.LoginDto;
import com.example.BookInfo.dto.SignupDto;
import com.example.BookInfo.dto.StudentPhoneDto;
import com.example.BookInfo.entity.Student;
import com.example.BookInfo.entity.StudentPhone;
import com.example.BookInfo.repository.StudentPhoneRepository;
import com.example.BookInfo.repository.StudentRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentPhoneRepository studentPhoneRepository;


    // Student Signup
    public String signup(SignupDto signupDto) {
        // Check if the student with the given email already exists
        if (studentRepository.existsByEmail(signupDto.getEmail())) {
            throw new IllegalArgumentException("Student with this email already exists!");
        }

        Student student = new Student();
        student.setId(signupDto.getId());
        student.setName(signupDto.getName());
        student.setEmail(signupDto.getEmail());
        student.setPassword(BCrypt.hashpw(signupDto.getPassword(), BCrypt.gensalt())); // Hash the password
        student.setDept(signupDto.getDept());
        student.setBatch(signupDto.getBatch());
        student.setAddress(signupDto.getAddress());  // Assuming you have an address field in Student
        student.setInterest(signupDto.getInterest()); // Assuming you have an interest field in Student

        studentRepository.save(student);


        // Add phone numbers from the DTO to the StudentPhone table
        if (signupDto.getPhones() != null) {
            for (StudentPhoneDto phoneDto : signupDto.getPhones()) {
                StudentPhone studentPhone = new StudentPhone();
                studentPhone.setStudentId(student.getId());
                studentPhone.setPhoneNumber(phoneDto.getPhoneNumber());
                studentPhone.setStudent(student);

                // Save each phone number associated with the student
                studentPhoneRepository.save(studentPhone);
            }
        }

        return "Student signed up successfully with phones!";
    }

    // Student Login
    public Student login(LoginDto loginDto) {
        Optional<Student> studentOpt = Optional.ofNullable(studentRepository.findByEmail(loginDto.getEmail()));

        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();

            if (BCrypt.checkpw(loginDto.getPassword(), student.getPassword())) {
                return student;  // Return the full student object
            } else {
                throw new IllegalArgumentException("Invalid email or password!");
            }
        } else {
            throw new IllegalArgumentException("Student not found with the given email!");
        }
    }


    // Fetch Student by Email
    public Student getStudentByEmail(String email) {
        // Find the student by email
        Optional<Student> studentOpt = Optional.ofNullable(studentRepository.findByEmail(email));
        if (studentOpt.isPresent()) {
            return studentOpt.get();
        } else {
            throw new IllegalArgumentException("Student not found with the given email!");
        }
    }

}
