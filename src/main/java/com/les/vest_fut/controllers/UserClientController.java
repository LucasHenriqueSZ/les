package com.les.vest_fut.controllers;

import com.les.vest_fut.Enums.CardFlag;
import com.les.vest_fut.Enums.Gender;
import com.les.vest_fut.Enums.MessagesSuccess;
import com.les.vest_fut.model.users.UserEntity;
import com.les.vest_fut.security.CustomUserDetails;
import com.les.vest_fut.service.ClientService;
import com.les.vest_fut.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final UserService userService;

    public UserClientController(ClientService clientService, UserService userService) {
        this.clientService = clientService;
        this.userService = userService;
    }

    @GetMapping("/perfil")
    public ModelAndView profileClient(@AuthenticationPrincipal CustomUserDetails sessionUser) {
        ModelAndView mv = new ModelAndView("public/pages/user/profile-client");
        mv.addObject("client", userService.getUserById(sessionUser.getUserEntity().getId()));
        mv.addObject("genders", Gender.getAll());
        mv.addObject("cardFlags", CardFlag.getAll());
        return mv;
    }


    @GetMapping("/novo")
    public ModelAndView newUserClient(UserEntity client) {
        ModelAndView mv = new ModelAndView("public/pages/user/create-user-client");
        mv.addObject("client", client);
        mv.addObject("genders", Gender.getAll());
        mv.addObject("cardFlags", CardFlag.getAll());
        return mv;
    }

    @PostMapping("/novo")
    public ModelAndView saveUserClient(@Valid @ModelAttribute("client") UserEntity client,
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
