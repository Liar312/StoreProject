package com.example.storeproject.controllers.RestControllers;

import com.example.storeproject.models.Product;
import com.example.storeproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/products")
public class RestProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/upload")
    public ResponseEntity<String> upLoad(
            Principal principal,
            @RequestParam("product") String productTittle,
            @RequestParam("author") String author,
            @RequestParam(value = "file1", required = false) MultipartFile file1,
            @RequestParam(value = "file2", required = false) MultipartFile file2,
            @RequestParam(value = "file3", required = false) MultipartFile file3
    ) {
        try {
            Product product = new Product();
            product.setTitle(productTittle);
            product.setAuthor(author);
            productService.saveProduct(principal, product, file1, file2, file3);
            return ResponseEntity.ok("Product upload succesfull");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading product" + e.getMessage());

        }

    }


}
