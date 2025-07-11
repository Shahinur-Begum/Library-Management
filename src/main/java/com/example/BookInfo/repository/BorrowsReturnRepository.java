package com.example.BookInfo.repository;

import com.example.BookInfo.entity.BorrowsReturn;
import com.example.BookInfo.entity.BorrowsReturn.BorrowsReturnKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowsReturnRepository extends JpaRepository<BorrowsReturn, BorrowsReturnKey> {
    @Query("SELECT b FROM BorrowsReturn b WHERE b.studentId = :studentId")
    List<BorrowsReturn> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT b FROM BorrowsReturn b WHERE b.studentId = :studentId AND b.due > 0")
    List<BorrowsReturn> findDueBooksByStudentId(@Param("studentId") Long studentId);

}
