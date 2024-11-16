package com.les.t_shirt_gen.controllers.products;

import com.les.t_shirt_gen.model.product.Product;
import com.les.t_shirt_gen.service.ProductService;
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
