package com.example.BookInfo.repository;


import com.example.BookInfo.entity.StudentPhone;
import com.example.BookInfo.entity.StudentPhone.StudentPhoneKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentPhoneRepository extends JpaRepository<StudentPhone, StudentPhoneKey> {
    List<StudentPhone> findByStudentId(Long studentId);
}