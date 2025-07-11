package com.example.BookInfo.repository;

import com.example.BookInfo.entity.Ebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EbookRepository extends JpaRepository<Ebook, Long> {

    Optional<Ebook> findByTitleIgnoreCase(@Param("title") String title);

}
