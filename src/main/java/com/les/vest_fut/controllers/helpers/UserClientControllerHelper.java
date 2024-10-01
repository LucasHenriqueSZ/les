package com.les.vest_fut.controllers.helpers;

import com.les.vest_fut.Enums.CardFlag;
import com.les.vest_fut.Enums.Gender;
import com.les.vest_fut.model.users.UserEntity;

import org.springframework.web.servlet.ModelAndView;

public class UserClientControllerHelper  extends CommonControllerHelper{

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

    public static ModelAndView redirectProfileView() {
        return new ModelAndView("redirect:/cliente/perfil");
    }

    public static ModelAndView redirectNewUserView() {
        return new ModelAndView("redirect:/cliente/novo");
    }

    public static ModelAndView redirectLoginView() {
        return new ModelAndView("redirect:/auth/login");
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

    public static ModelAndView redirectChekoutView() {
        return new ModelAndView("redirect:/checkout");
    }
}