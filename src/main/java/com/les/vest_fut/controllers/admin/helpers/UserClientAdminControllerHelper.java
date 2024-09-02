package com.les.vest_fut.controllers.admin.helpers;

import com.les.vest_fut.controllers.helpers.CommonControllerHelper;
import com.les.vest_fut.model.users.UserEntity;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public class UserClientAdminControllerHelper extends CommonControllerHelper {

    public static ModelAndView redirectAdminClient() {
        return new ModelAndView("redirect:/admin/clientes");
    }


    public static ModelAndView prepareAdminClientView(UserEntity client, List<UserEntity> clients, boolean hasErrors) {
        ModelAndView mv =  new ModelAndView("private/pages/clients-adm");
        mv.addObject("clientTemplate", client);
        mv.addObject("clients", clients);
        mv.addObject("hasErrors", hasErrors);
        return mv;
    }
}
