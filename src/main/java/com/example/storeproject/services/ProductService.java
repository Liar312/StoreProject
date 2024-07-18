package com.example.storeproject.services;

import com.example.storeproject.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.storeproject.models.Product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    private long ID = 0;

    public List<Product> ListProducts(String author){
        if(author != null) return productRepository.findByAuthor(author);

        return productRepository.findAll();
    }
    public List<Product> ListByTitle(String title){
        if(title!=null) return productRepository.findByTitle(title);
        return productRepository.findAll();//обязательно данный метод должен быть реализован в репе
    }
    private List<Product> products = new ArrayList<>();
    {
        products.add(new Product(1,"Example tittle","Example authort","Examle city",5000,"Example description"));
        products.add(new Product(2,"Gaming platform","Sony","New York",52000,"Simple description"));
    }
    public List<Product> listOfAll(String title){
        return products;             //simple return added products
    }
    @Transactional
    public void saveProduct(Product product){
//        product.setId(++ID); этот функционал выполняет @GeneratedValue
        log.info("Saving new {}",product);//метод toString подставит сюда строковое представление product
        //products.add(product);не нужная реализация без Jparepository
        productRepository.save(product);
    }
    public void deleteProduct(long id){
//        products.removeIf(product -> product.getId() == id);
        productRepository.deleteById(id);
    }
    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);//пробрасываем ошибку чтобы сделать соответсвие типов
    }

}
