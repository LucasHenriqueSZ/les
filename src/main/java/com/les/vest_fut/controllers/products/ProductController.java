package com.les.vest_fut.controllers.products;

import com.les.vest_fut.model.product.Product;
import com.les.vest_fut.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/produtos")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ModelAndView getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return new ModelAndView("public/pages/products", "products", products);
    }
}
