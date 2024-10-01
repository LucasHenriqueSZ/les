package com.les.vest_fut.controllers.admin;

import com.les.vest_fut.Enums.MessagesSuccess;
import com.les.vest_fut.controllers.admin.helpers.OrdersAdminControllerHelper;
import com.les.vest_fut.model.order.Order;
import com.les.vest_fut.model.order.OrderStatus;
import com.les.vest_fut.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/pedidos")
@AllArgsConstructor
public class OrdersAdmController {

    private OrderService orderService;

    @GetMapping
    public ModelAndView listOrders() {
        List<Order> orders = orderService.findAll();
        ModelAndView mv = new ModelAndView("private/pages/orders-adm");
        mv.addObject("orders", orders);
        mv.addObject("ordersStatus", OrderStatus.getAll());
        return mv;
    }

    @PostMapping("/alter-status")
    public ModelAndView updateOrderStatus(@RequestParam Long id, @RequestParam OrderStatus status, RedirectAttributes attributes) {
        try {
            orderService.updateOrderStatus(id, status);
            OrdersAdminControllerHelper.addSuccessMessage(attributes, MessagesSuccess.ORDER_STATUS_SUCCESS);
            return OrdersAdminControllerHelper.redirectAdminOrders();
        } catch (Exception e) {
            OrdersAdminControllerHelper.addErrorMessage(attributes, e.getMessage());
            return OrdersAdminControllerHelper.redirectAdminOrders();
        }
    }
}
