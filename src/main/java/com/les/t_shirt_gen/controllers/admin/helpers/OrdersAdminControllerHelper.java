package com.les.t_shirt_gen.controllers.admin.helpers;

import com.les.t_shirt_gen.controllers.helpers.CommonControllerHelper;
import org.springframework.web.servlet.ModelAndView;

public class OrdersAdminControllerHelper extends CommonControllerHelper {
    public static ModelAndView redirectAdminOrders() {
        return new ModelAndView("redirect:/admin/pedidos");
    }
}
