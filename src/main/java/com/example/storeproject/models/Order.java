package com.example.storeproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="orders")
public class Order {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="order_date")
    private LocalDateTime orderDate;
    @Column(name="Status")
    private String status;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @PrePersist
    private void init(){
        orderDate = LocalDateTime.now();
    }

}
