package com.les.vest_fut.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class HomeAdmController {

    @GetMapping()
    public ModelAndView home() {
        return new ModelAndView("private/pages/home-adm");
    }

}