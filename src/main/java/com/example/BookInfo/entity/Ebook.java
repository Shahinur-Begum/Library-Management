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
@Table(name = "Ebook")  // Matches the table name in the database
public class Ebook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")  // Match the database column name
    private Long Id;

    @Column(name = "Cover")
    private String cover;

    @Column(name = "Title")
    private String title;

    @Column(name = "PDF_URL")
    private String pdf;

    @Column(name = "Audio_Link")
    private String audioLink;

    @OneToMany(mappedBy = "ebook", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Access> accesses;
}
