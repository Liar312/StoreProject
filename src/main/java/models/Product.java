package models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Product {
    @Id
    private long id;
    private String tittle;
    private String author;
    private String city;
    private int price;
    private String description;

}
