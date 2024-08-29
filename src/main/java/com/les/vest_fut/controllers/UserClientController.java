package com.les.vest_fut.controllers;

import com.les.vest_fut.Enums.MessagesSuccess;
import com.les.vest_fut.controllers.helpers.UserClientControllerHelper;
import com.les.vest_fut.model.users.UserEntity;
import com.les.vest_fut.security.CustomUserDetails;
import com.les.vest_fut.service.ClientService;
import com.les.vest_fut.service.UserService;
import com.les.vest_fut.utils.groups.OnBasicInfoValidation;
import com.les.vest_fut.utils.groups.OnCreate;
import com.les.vest_fut.utils.groups.OnPasswordValidation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
    public ModelAndView profileClient(@AuthenticationPrincipal CustomUserDetails sessionUser, UserEntity client) {
        UserEntity sessionUserEntity = userService.getUserById(sessionUser.getUserEntity().getId());
        return UserClientControllerHelper.prepareProfileView(client.hasValidObject() ? client : sessionUserEntity);
    }

    @GetMapping("/novo")
    public ModelAndView newUserClient(UserEntity client) {
        return UserClientControllerHelper.prepareNewUserView(client);
    }

    @PostMapping("/novo")
    public ModelAndView saveUserClient(@Validated(OnCreate.class) @ModelAttribute("client") UserEntity client,
                                       BindingResult bindingResult,
                                       RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return UserClientControllerHelper.prepareNewUserView(client);
        }

        try {
            clientService.saveClient(client);
            UserClientControllerHelper.addSuccessMessage(attributes, MessagesSuccess.CLIENT_REGISTERED);
            return new ModelAndView("redirect:/auth/login");
        } catch (Exception e) {
            UserClientControllerHelper.addErrorMessage(attributes, e.getMessage());
            return new ModelAndView("redirect:/cliente/novo");
        }
    }

    @PostMapping("/editBasicInfo")
    public ModelAndView editBasicInfoUserClient(@Validated(OnBasicInfoValidation.class) @ModelAttribute("client") UserEntity client,
                                                BindingResult bindingResult,
                                                @AuthenticationPrincipal CustomUserDetails sessionUser,
                                                RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            UserEntity currentUser = userService.getUserById(sessionUser.getUserEntity().getId());
            UserClientControllerHelper.restoreClientData(client, currentUser);
            return UserClientControllerHelper.prepareProfileView(client);
        }

        try {
            clientService.editBasicInfoClient(client, sessionUser.getUserEntity().getId());
            UserClientControllerHelper.addSuccessMessage(attributes, MessagesSuccess.CLIENT_UPDATED);
            return new ModelAndView("redirect:/cliente/perfil");
        } catch (Exception e) {
            UserClientControllerHelper.addErrorMessage(attributes, e.getMessage());
            return new ModelAndView("redirect:/cliente/perfil");
        }
    }

    @PostMapping("/editPassword")
    public ModelAndView editPasswordUserClient(@RequestParam("currentPassword") String currentPassword,
                                               @Validated(OnPasswordValidation.class) @ModelAttribute("client") UserEntity client,
                                               BindingResult bindingResult,
                                               @AuthenticationPrincipal CustomUserDetails sessionUser,
                                               RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            UserEntity currentUser = userService.getUserById(sessionUser.getUserEntity().getId());
            UserClientControllerHelper.restoreClientData(client, currentUser);
            return UserClientControllerHelper.prepareProfileView(client);
        }

        try {
            clientService.editPasswordClient(client, currentPassword, sessionUser.getUserEntity().getId());
            UserClientControllerHelper.addSuccessMessage(attributes, MessagesSuccess.PASSWORD_UPDATED);
            return new ModelAndView("redirect:/cliente/perfil");
        } catch (Exception e) {
            UserClientControllerHelper.addErrorMessage(attributes, e.getMessage());
            return new ModelAndView("redirect:/cliente/perfil");
        }
    }
}