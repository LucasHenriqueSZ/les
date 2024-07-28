package com.les.vest_fut.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cliente")
public class UserClientController {

    @RequestMapping("/novo")
    public ModelAndView newUserClient() {
        return new ModelAndView("public/pages/user/create-user-client");
    }
}
