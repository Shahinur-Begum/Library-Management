package com.example.BookInfo.repository;

import com.example.BookInfo.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);

    boolean existsByEmail(String mail);
}
