package com.example.storeproject.controllers;

import lombok.RequiredArgsConstructor;
import com.example.storeproject.models.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.storeproject.services.ProductService;


@Controller
@RequiredArgsConstructor// позволяет не писать в ручную кодом конструктор
public class productController {
    private final ProductService productService;
//    @GetMapping("/")
//    public String products(){
//        return "/";
//    }



    @GetMapping("/")
    public <model> String products(@RequestParam(name = "title",required = false) String title, Model model){
        model.addAttribute("products",productService.listOfAll(title));
        return "products";
    }


    @PostMapping("product/create")
    public String CreateProduct(Product product){
        productService.saveProduct(product);
        return"redirect:/"; //обновление списка
    }


    @PostMapping("/product/delete/{id}")
    public String DeleteProduct(@PathVariable  Long id ){
        productService.deleteProduct(id);
        return "redirect:/";
    }


    @GetMapping("/products/{id}")
    public String productInfo(@PathVariable Long id,Model model){
        model.addAttribute("product",productService.getProductById(id));
        return "product-info";
    }
}
