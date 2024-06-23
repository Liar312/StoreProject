package controllers;

import lombok.RequiredArgsConstructor;
import models.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import services.ProductService;


@Controller
@RequiredArgsConstructor// позволяет не писать в ручную кодом конструктор
public class productController {
    private final ProductService productService;
//    @GetMapping("/")
//    public String products(){
//        return "/";
//    }

    @GetMapping("/products")
    public String products(Model model){
        model.addAttribute("products",productService.listOfAll());
        return "products";
    }
    @PostMapping("product/create")
    public String CreateProduct(Product product){
        productService.saveProduct(product);
        return"reidrect:/"; //обновление списка
    }
}
