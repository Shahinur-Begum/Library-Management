package com.example.BookInfo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "access")  // Matches the table name in the database
@IdClass(Access.AccessKey.class)
public class Access implements Serializable {

    @Id
    @Column(name = "ebook_id")
    private Long ebookId;

    @Id
    @Column(name = "student_id")
    private Long studentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ebook_id", insertable = false, updatable = false)
    private Ebook ebook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private Student student;

    // Composite Key Class
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccessKey implements Serializable {
        private Long ebookId;
        private Long studentId;
    }
}

