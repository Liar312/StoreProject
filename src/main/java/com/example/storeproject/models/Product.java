package com.example.storeproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;


    private String title;
    private String author;
    private String city;
    private int price;

    @Column(name="description",columnDefinition = "text")//указываем тип текст тк варчар 128
    private String description;

}
