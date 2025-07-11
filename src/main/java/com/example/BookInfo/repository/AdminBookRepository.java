package com.example.BookInfo.repository;

import com.example.BookInfo.entity.AdminBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminBookRepository extends JpaRepository<AdminBook, Long> {

}
