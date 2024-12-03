package com.les.t_shirt_gen.controllers;

import com.les.t_shirt_gen.Enums.MessagesSuccess;
import com.les.t_shirt_gen.controllers.helpers.UserClientControllerHelper;
import com.les.t_shirt_gen.model.users.Address;
import com.les.t_shirt_gen.model.users.Card;
import com.les.t_shirt_gen.model.users.UserEntity;
import com.les.t_shirt_gen.security.CustomUserDetails;
import com.les.t_shirt_gen.service.ClientService;
import com.les.t_shirt_gen.service.CuponService;
import com.les.t_shirt_gen.service.UserService;
import com.les.t_shirt_gen.utils.groups.OnBasicInfoValidation;
import com.les.t_shirt_gen.utils.groups.OnCreate;
import com.les.t_shirt_gen.utils.groups.OnPasswordValidation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cliente")
@AllArgsConstructor
public class UserClientController {

    private final ClientService clientService;
    private final UserService userService;


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
            return UserClientControllerHelper.redirectLoginView();
        } catch (Exception e) {
            UserClientControllerHelper.addErrorMessage(attributes, e.getMessage());
            return UserClientControllerHelper.redirectNewUserView();
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
            return UserClientControllerHelper.redirectProfileView();
        } catch (Exception e) {
            UserClientControllerHelper.addErrorMessage(attributes, e.getMessage());
            return UserClientControllerHelper.redirectProfileView();
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
            return UserClientControllerHelper.redirectProfileView();
        } catch (Exception e) {
            UserClientControllerHelper.addErrorMessage(attributes, e.getMessage());
            return UserClientControllerHelper.redirectProfileView();
        }
    }

    @PostMapping("/removeCard")
    public ModelAndView removeCard(@RequestParam("cardId") Long cardId,
                                   @AuthenticationPrincipal CustomUserDetails sessionUser,
                                   RedirectAttributes attributes) {
        try {
            clientService.removeCard(cardId, sessionUser.getUserEntity().getId());
            UserClientControllerHelper.addSuccessMessage(attributes, MessagesSuccess.CARD_REMOVED);
            return UserClientControllerHelper.redirectProfileView();
        } catch (Exception e) {
            UserClientControllerHelper.addErrorMessage(attributes, e.getMessage());
            return UserClientControllerHelper.redirectProfileView();
        }
    }

    @PostMapping("/removeAddress")
    public ModelAndView removeAddress(@RequestParam("addressId") Long addressId,
                                      @AuthenticationPrincipal CustomUserDetails sessionUser,
                                      RedirectAttributes attributes) {
        try {
            clientService.removeAddress(addressId, sessionUser.getUserEntity().getId());
            UserClientControllerHelper.addSuccessMessage(attributes, MessagesSuccess.ADDRESS_REMOVED);
            return UserClientControllerHelper.redirectProfileView();
        } catch (Exception e) {
            UserClientControllerHelper.addErrorMessage(attributes, e.getMessage());
            return UserClientControllerHelper.redirectProfileView();
        }
    }

    @PostMapping("/saveCard")
    public ModelAndView saveCard(@Validated @ModelAttribute("card") Card card,
                                 BindingResult bindingResult,
                                 @AuthenticationPrincipal CustomUserDetails sessionUser,
                                 @RequestParam(name = "returnTo", defaultValue = "profile") String returnTo,
                                 RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            UserClientControllerHelper.addErrorMessage(attributes, "Erro ao salvar cartão");
            if ("checkout".equals(returnTo)) {
                return UserClientControllerHelper.redirectChekoutView();
            }
            return UserClientControllerHelper.redirectProfileView();
        }

        try {
            clientService.saveCard(card, sessionUser.getUserEntity().getId());
            UserClientControllerHelper.addSuccessMessage(attributes, card.getId() == null ? MessagesSuccess.CARD_REGISTERED : MessagesSuccess.CARD_UPDATED);
            if ("checkout".equals(returnTo)) {
                return UserClientControllerHelper.redirectChekoutView();
            }
            return UserClientControllerHelper.redirectProfileView();
        } catch (Exception e) {
            UserClientControllerHelper.addErrorMessage(attributes, e.getMessage());
            if ("checkout".equals(returnTo)) {
                return UserClientControllerHelper.redirectChekoutView();
            }
            return UserClientControllerHelper.redirectProfileView();
        }
    }

    @PostMapping("/saveAddress")
    public ModelAndView saveAddress(@Validated @ModelAttribute("address") Address address,
                                    BindingResult bindingResult,
                                    @AuthenticationPrincipal CustomUserDetails sessionUser,
                                    @RequestParam(name = "returnTo", defaultValue = "profile") String returnTo,
                                    RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            UserClientControllerHelper.addErrorMessage(attributes, "Erro ao salvar endereço");
            if ("checkout".equals(returnTo)) {
                return UserClientControllerHelper.redirectChekoutView();
            }
            return UserClientControllerHelper.redirectProfileView();
        }

        try {
            clientService.saveAddress(address, sessionUser.getUserEntity().getId());
            UserClientControllerHelper.addSuccessMessage(attributes, address.getId() == null ? MessagesSuccess.ADDRESS_REGISTERED : MessagesSuccess.ADDRESS_UPDATED);

            if ("checkout".equals(returnTo)) {
                return UserClientControllerHelper.redirectChekoutView();
            }
            return UserClientControllerHelper.redirectProfileView();
        } catch (Exception e) {
            UserClientControllerHelper.addErrorMessage(attributes, e.getMessage());

            if ("checkout".equals(returnTo)) {
                return UserClientControllerHelper.redirectChekoutView();
            }
            return UserClientControllerHelper.redirectProfileView();
        }
    }
}