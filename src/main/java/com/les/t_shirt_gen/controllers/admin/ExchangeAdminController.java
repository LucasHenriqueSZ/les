package com.les.t_shirt_gen.controllers.admin;

import com.les.t_shirt_gen.Enums.MessagesSuccess;
import com.les.t_shirt_gen.controllers.admin.helpers.ExchangeAdminControllerHelper;
import com.les.t_shirt_gen.model.exchange.ExchangeRequest;
import com.les.t_shirt_gen.model.exchange.ExchangeStatus;
import com.les.t_shirt_gen.service.ExchangeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/admin/exchanges")
public class ExchangeAdminController {

    private final ExchangeService exchangeService;

    @GetMapping
    public ModelAndView listAllExchangeRequests() {
        List<ExchangeRequest> exchangeRequests = exchangeService.findAll();

        ModelAndView mv = new ModelAndView("private/pages/exchange-requests");

        mv.addObject("exchangeRequests", exchangeRequests);
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView viewExchangeRequestById(@PathVariable String id) {
        Long idL = Long.parseLong(id);
        ExchangeRequest exchangeRequest = exchangeService.findById(idL).orElse(null);

        ModelAndView mv = new ModelAndView("private/pages/exchange-requests-item");
        mv.addObject("exchange", exchangeRequest);
        return mv;
    }

    @PostMapping("/{id}/update-status")
    public ModelAndView updateExchangeRequestStatus(@PathVariable String id, ExchangeStatus status, RedirectAttributes attributes) {
        Long idL = Long.parseLong(id);
        try {
            exchangeService.updateExchangeRequestStatus(idL, status);
            ExchangeAdminControllerHelper.addSuccessMessage(attributes, MessagesSuccess.SUCCESS_EXCHANGE_STATUS_UPDATE);
            return ExchangeAdminControllerHelper.redirectExchangeItem(idL);
        } catch (Exception e) {
            ExchangeAdminControllerHelper.addErrorMessage(attributes, e.getMessage());
            return ExchangeAdminControllerHelper.redirectExchangeItem(idL);
        }
    }


    @PostMapping("/{id}/finalize")
    public ModelAndView finalizeExchangeRequest(@PathVariable String id,
                                                @RequestParam(name = "action", required = false) String action,
                                                @RequestParam(name = "returnToStock", required = false) boolean returnToStock,
                                                @RequestParam(name = "observations", required = false) String observations,
                                                RedirectAttributes attributes) {
        Long idL = Long.parseLong(id);
        try {
            if (action.equals("approve")) {
                exchangeService.approveExchangeRequest(idL, returnToStock, observations);
                ExchangeAdminControllerHelper.addSuccessMessage(attributes, MessagesSuccess.SUCCESS_EXCHANGE_APPROVE);
            }

            if (action.equals("reject")) {
                exchangeService.rejectExchangeRequest(idL, observations);
                ExchangeAdminControllerHelper.addSuccessMessage(attributes, MessagesSuccess.SUCCESS_EXCHANGE_REJECT);
            }
            return ExchangeAdminControllerHelper.redirectExchange();
        } catch (Exception e) {
            ExchangeAdminControllerHelper.addErrorMessage(attributes, e.getMessage());
            return ExchangeAdminControllerHelper.redirectExchangeItem(idL);
        }
    }

}
