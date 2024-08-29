package com.les.vest_fut.controllers.helpers;

import com.les.vest_fut.Enums.CardFlag;
import com.les.vest_fut.Enums.Gender;
import com.les.vest_fut.Enums.MessagesSuccess;
import com.les.vest_fut.model.users.UserEntity;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class UserClientControllerHelper {

    public static ModelAndView prepareProfileView(UserEntity client) {
        ModelAndView mv = new ModelAndView("public/pages/user/profile-client");
        mv.addObject("client", client);
        mv.addObject("genders", Gender.getAll());
        mv.addObject("cardFlags", CardFlag.getAll());
        return mv;
    }

    public static ModelAndView prepareNewUserView(UserEntity client) {
        ModelAndView mv = new ModelAndView("public/pages/user/create-user-client");
        mv.addObject("client", client);
        mv.addObject("genders", Gender.getAll());
        mv.addObject("cardFlags", CardFlag.getAll());
        return mv;
    }

    public static void restoreClientData(UserEntity client, UserEntity currentUser) {
        client.setAddresses(currentUser.getAddresses());
        client.setCards(currentUser.getCards());
        client.setName(currentUser.getName());
        client.setEmail(currentUser.getEmail());
        client.setPhone(currentUser.getPhone());
        client.setCpf(currentUser.getCpf());
        client.setGender(currentUser.getGender());
    }

    public static void addSuccessMessage(RedirectAttributes attributes, MessagesSuccess message) {
        attributes.addFlashAttribute("mensagem", message.getMessage());
    }

    public static void addErrorMessage(RedirectAttributes attributes, String errorMessage) {
        attributes.addFlashAttribute("alert", errorMessage);
    }
}