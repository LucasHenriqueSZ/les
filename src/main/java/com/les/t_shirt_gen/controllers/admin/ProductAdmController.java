package com.les.t_shirt_gen.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/products")
public class ProductAdmController {

    @GetMapping("/new")
    public ModelAndView home() {
        return new ModelAndView("private/pages/new-product");
    }

}
