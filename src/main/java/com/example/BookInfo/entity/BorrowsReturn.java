package com.example.BookInfo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "borrows_return")
@IdClass(BorrowsReturn.BorrowsReturnKey.class)
public class BorrowsReturn implements Serializable {

    @Id
    @Column(name = "student_id")
    private Long studentId;

    @Id
    @Column(name = "book_id")
    private Long bookId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    @JsonBackReference
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    private Book book;

    @Column(name = "issued_date", nullable = false)
    private Date issuedDate;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "due", nullable = false)
    private int due = 0;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BorrowsReturnKey implements Serializable {
        private Long studentId;
        private Long bookId;
    }
}
