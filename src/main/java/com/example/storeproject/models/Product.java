package com.example.storeproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "city")
    private String city;
    @Column(name = "price")
    private int price;
    @Column(name="description",columnDefinition = "text")//указываем тип текст тк варчар 128
    private String description;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "product")//Mapedby указатель для таблицы images на объявленный там product
    private List <Image> images = new ArrayList<>();
    private Long previewImageId;//при создании товара будем указывать id для того чтобы поставть на превью
    private LocalDateTime dateOfCreating;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn//foreign key
    private User user;


    @PrePersist//метод вызывает перед сохранением сущности
    private void init(){
        dateOfCreating = LocalDateTime.now();
    }
    public void addImageToProduct(Image image){
        image.setProduct(this);//setproduct тянется из image тк там создается объект и с аннотацией ломбок и соотв таким методом
        images.add(image);
    }

}
