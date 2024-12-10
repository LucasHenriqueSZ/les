package com.les.t_shirt_gen.controllers.helpers;

import com.les.t_shirt_gen.Enums.MessagesSuccess;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class CommonControllerHelper {
    public static void addSuccessMessage(RedirectAttributes attributes, MessagesSuccess message) {
        attributes.addFlashAttribute("mensagem", message.getMessage());
    }

    public static void addErrorMessage(RedirectAttributes attributes, String errorMessage) {
        attributes.addFlashAttribute("alert", errorMessage);
    }
}
