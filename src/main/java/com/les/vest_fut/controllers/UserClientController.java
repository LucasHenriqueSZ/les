package com.les.vest_fut.controllers;

import com.les.vest_fut.Enums.Gender;
import com.les.vest_fut.Enums.MessagesSuccess;
import com.les.vest_fut.model.Client;
import com.les.vest_fut.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/cliente")
public class UserClientController {

    private final ClientService clientService;

    public UserClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/novo")
    public ModelAndView newUserClient(Client client) {
        ModelAndView mv = new ModelAndView("public/pages/user/create-user-client");
        mv.addObject("client", client);
        mv.addObject("genders", Gender.getAll());
        return mv;
    }

    @PostMapping("/novo")
    public ModelAndView saveUserClient(@Valid @ModelAttribute("client") Client client,
                                       BindingResult bindingResult,
                                       RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return newUserClient(client);
        }
        ModelAndView mv = new ModelAndView();
        try {
            clientService.saveClient(client);
            mv.setViewName("redirect:/auth/login");
            attributes.addFlashAttribute("mensagem", MessagesSuccess.CLIENT_REGISTERED.getMessage());
        } catch (Exception e) {
            attributes.addFlashAttribute("alert", e.getMessage());
            mv.setViewName("redirect:/cliente/novo");
        }
        return mv;
    }


}
