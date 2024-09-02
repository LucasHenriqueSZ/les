package com.les.vest_fut.controllers.admin;

import com.les.vest_fut.Enums.MessagesSuccess;
import com.les.vest_fut.controllers.admin.helpers.UserClientAdminControllerHelper;
import com.les.vest_fut.model.users.UserEntity;
import com.les.vest_fut.service.ClientService;
import com.les.vest_fut.utils.groups.OnPasswordValidation;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/clientes")
public class UserClientAdminController {

    private final ClientService clientService;

    public UserClientAdminController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ModelAndView adminClient(UserEntity client) {
        return UserClientAdminControllerHelper.prepareAdminClientView(client, clientService.getAllClients(), false);
    }

    @PostMapping("/alter-status")
    public ModelAndView alterStatus(Long id, RedirectAttributes attributes) {
        clientService.alterStatus(id);
        UserClientAdminControllerHelper.addSuccessMessage(attributes, MessagesSuccess.CLIENT_STATUS_ALTERED);
        return UserClientAdminControllerHelper.redirectAdminClient();
    }

    @PostMapping("/editPassword")
    public ModelAndView editPassword(@RequestParam("clientId") Long clientId,
                                     @Validated(OnPasswordValidation.class) @ModelAttribute("clientTemplate") UserEntity client,
                                     BindingResult bindingResult,
                                     RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            client.setId(clientId);
            return UserClientAdminControllerHelper.prepareAdminClientView(client, clientService.getAllClients(), true);
        }
        clientService.editPasswordAdmin(client, clientId);
        UserClientAdminControllerHelper.addSuccessMessage(attributes, MessagesSuccess.PASSWORD_UPDATED);
        return UserClientAdminControllerHelper.redirectAdminClient();
    }

}
