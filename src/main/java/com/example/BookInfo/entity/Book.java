package com.example.BookInfo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Cover")
    private String cover;

    @Column(name = "Title")
    private String title;

    @Column(name = "Author")
    private String author;

    @Column(name = "Category")
    private String category;

    @Column(name = "Available")
    private Long available;

    @OneToMany(mappedBy = "book")
    private Set<AdminBook> adminBooks;  // To keep track of admins managing the book

    // Many-to-Many relationship with students via BorrowsReturn
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<BorrowsReturn> borrowsReturns;
}
