package com.les.vest_fut.controllers.admin.helpers;

import com.les.vest_fut.controllers.helpers.CommonControllerHelper;
import com.les.vest_fut.model.users.UserEntity;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public class OrdersAdminControllerHelper extends CommonControllerHelper {
    public static ModelAndView redirectAdminOrders() {
        return new ModelAndView("redirect:/admin/pedidos");
    }
}
