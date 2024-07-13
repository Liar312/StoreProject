package services;

import com.example.storeproject.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.Product;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    private long ID = 0;

    public List<Product> ListProducts(String author){
        if(author != null) productRepository.findByAuthor(author);

        return productRepository.findAll();
    }
    private List<Product> products = new ArrayList<>();
    {
        products.add(new Product(1,"Example tittle","Example authort","Examle city",5000,"Example description"));
        products.add(new Product(2,"Gaming platform","Sony","New York",52000,"Simple description"));
    }
    public List<Product> listOfAll(){
        return products;             //simple return added products
    }
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
