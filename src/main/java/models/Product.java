package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

public class Product {
    private long id;
    private String tittle;
    private String author;
    private String city;
    private int price;
    private String description;

}
