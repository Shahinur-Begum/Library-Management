package com.example.BookInfo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double amount; // Amount paid by the user

    @Column(nullable = false)
    private String method; // "Bkash", "Nagad", "Rocket"

    @ManyToOne
    @JoinColumn(name = "Student_id", nullable = false) // Foreign key reference to Student
    @JsonBackReference
    private Student student; // Reference to Student entity

    @Column(nullable = false)
    private boolean paid; // Payment status: true if amount is 30, else false

    @Column(nullable = false, unique = true)
    private String transactionId; // Transaction ID for the payment
}