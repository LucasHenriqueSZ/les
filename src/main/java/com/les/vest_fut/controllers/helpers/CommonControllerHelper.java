package com.les.vest_fut.controllers.helpers;

import com.les.vest_fut.Enums.MessagesSuccess;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class CommonControllerHelper {
    public static void addSuccessMessage(RedirectAttributes attributes, MessagesSuccess message) {
        attributes.addFlashAttribute("mensagem", message.getMessage());
    }

    public static void addErrorMessage(RedirectAttributes attributes, String errorMessage) {
        attributes.addFlashAttribute("alert", errorMessage);
    }
}
