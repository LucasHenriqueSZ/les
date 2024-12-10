package com.les.t_shirt_gen.controllers.admin;

import com.les.t_shirt_gen.Enums.MessagesSuccess;
import com.les.t_shirt_gen.controllers.admin.helpers.UserClientAdminControllerHelper;
import com.les.t_shirt_gen.model.users.UserEntity;
import com.les.t_shirt_gen.service.ClientService;
import com.les.t_shirt_gen.utils.groups.OnPasswordValidation;
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
