package com.les.t_shirt_gen.controllers.admin.helpers;

import com.les.t_shirt_gen.controllers.helpers.CommonControllerHelper;
import com.les.t_shirt_gen.model.users.UserEntity;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public class ExchangeAdminControllerHelper extends CommonControllerHelper {


    public static ModelAndView redirectExchangeItem(Long id) {
        return new ModelAndView("redirect:/admin/exchanges/" + id);
    }

    public static ModelAndView redirectExchange() {
        return new ModelAndView("redirect:/admin/exchanges");
    }
}
