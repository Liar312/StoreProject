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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@RequiredArgsConstructor// позволяет не писать в ручную кодом конструктор
public class productController {
    private final ProductService productService;


@GetMapping("/")
public String products(@RequestParam(name = "title", required = false) String title, Model model) {

    model.addAttribute("products", productService.ListByTitle(title));
    return "products";
}
    @PostMapping("product/create")
    public String CreateProduct(@RequestParam("file1") MultipartFile file1,@RequestParam MultipartFile file2,@RequestParam MultipartFile file3, Product product) throws IOException {
    productService.saveProduct(product,file1,file2,file3);//не забыть пробросить исключение если оно есть в сервисной части
        return"redirect:/"; //обновление списка
    }


    @PostMapping("/product/delete/{id}")
    public String DeleteProduct(@PathVariable  Long id ){
        productService.deleteProduct(id);
        return "redirect:/";
    }


    @GetMapping("/products/{id}")
    public String productInfo(@PathVariable Long id,Model model){
        Product product = productService.getProductById(id);
        model.addAttribute("product",productService.getProductById(id));
        model.addAttribute("images",product.getImages());
        return "product-info";
    }
}






//    @GetMapping("/")
//    public <model> String products(@RequestParam(name = "title",required = false) String title, Model model){
//        model.addAttribute("products",productService.listOfAll(title));
//        return "products";
//    }


//    @GetMapping("/")
//    public <model> String products(@RequestParam(name = "title",required = false) String title, Model model){
//        model.addAttribute("products",productService.listOfAll(title));
//        return "products";
//    }


//    @GetMapping("/")
//    public String products(){
//        return "/";
//    }
