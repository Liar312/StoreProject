package services;

import models.Product;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductService {

    private long ID = 0;
    private List<Product> products = new ArrayList<>();
    {
        products.add(new Product(1,"Example tittle","Example authort","Examle city",5000,"Example description"));
        products.add(new Product(2,"Gaming platform","Sony","New York",52000,"Simple description"));
    }
    public List<Product> listOfAll(){
        return products;             //simple return added products
    }
    public void saveProduct(Product product){
        product.setId(++ID);
        products.add(product);
    }
    public void deleteProduct(long id){
        products.removeIf(product -> product.getId() == id);
    }

}
