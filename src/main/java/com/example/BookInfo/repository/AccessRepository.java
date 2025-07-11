package com.example.BookInfo.repository;

import com.example.BookInfo.entity.Access;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessRepository extends JpaRepository<Access, Access.AccessKey> {
}
