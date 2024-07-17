package com.example.storeproject.repositories;

import com.example.storeproject.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
List<Product> findByAuthor(String author);
List<Product> findByTitle(String title);

}
