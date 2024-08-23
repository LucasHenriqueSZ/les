package com.les.vest_fut.controllers;

import com.les.vest_fut.Enums.CardFlag;
import com.les.vest_fut.Enums.Gender;
import com.les.vest_fut.Enums.MessagesSuccess;
import com.les.vest_fut.model.users.Address;
import com.les.vest_fut.model.users.Card;
import com.les.vest_fut.model.users.User;
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

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/cliente")
public class UserClientController {

    private final ClientService clientService;

    public UserClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/perfil")
    public ModelAndView profileClient() {
        ModelAndView mv = new ModelAndView("public/pages/user/profile-client");
        mv.addObject("client", getMockClient());
        mv.addObject("genders", Gender.getAll());
        return mv;
    }


    @GetMapping("/novo")
    public ModelAndView newUserClient(User client) {
        ModelAndView mv = new ModelAndView("public/pages/user/create-user-client");
        mv.addObject("client", client);
        mv.addObject("genders", Gender.getAll());
        mv.addObject("cardFlags", CardFlag.getAll());
        return mv;
    }

    @PostMapping("/novo")
    public ModelAndView saveUserClient(@Valid @ModelAttribute("client") User client,
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

    private User getMockClient() {
        // TODO remover mock de teste
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address("12345-678", "Rua Exemplo", "123", "Apto 101", "Bairro Exemplo", "Cidade Exemplo", "SP"));
        addresses.add(new Address("12345-678", "Rua Exemplo", "123", "Apto 101", "Bairro Exemplo", "Cidade Exemplo", "SP"));
        addresses.add(new Address("12345-678", "Rua Exemplo", "123", "Apto 101", "Bairro Exemplo", "Cidade Exemplo", "SP"));

        List<Card> cards = new ArrayList<>();
        cards.add(new Card("1234567812345678", "João Exemplo", "12/25", "123"));

        User client = new User();
        client.setName("João Exemplo");
        client.setCpf("123.456.789-00");
        client.setGender(Gender.MALE);
        client.setEmail("joao.exemplo@example.com");
        client.setPhone("(11) 91234-5678");
        client.setPassword("senhaSegura123");
        client.setConfirmPassword("senhaSegura123");
        client.setAddresses(addresses);
        client.setCards(cards);

        return client;
    }



}
