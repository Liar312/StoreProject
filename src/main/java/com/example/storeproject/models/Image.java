package com.example.storeproject.models;

import com.example.storeproject.repositories.ProductRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "images")
@Data
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//рекомуендуется использовать аннотации persistance в случае если понадобится перейти на другую orm
   @Column(name = "id")//создавать атрибуты для изображения надо опираясь на методы MultiPartFile(если что)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name ="originalName")
    private String originalName;
    @Column(name="size")
    private Long size;
    @Column(name="contentType")
    private String contentType;
    private boolean isPreviewImage;//флажок для обрабатывания превьюшной фото
    @Lob//используется для указания того что в бд это будет большой объем данных(BLob используется для хранения двоичных CLob текстовых данных)
    private byte[] bytes;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    private Product product;//связываем сущности и указываем что у нас идет много фото к одному продукту
}
