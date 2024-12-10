package com.les.t_shirt_gen.exceptions;

import com.les.t_shirt_gen.Enums.MessagesExceptions;

public class UniqueFieldException extends CustomException {
    public UniqueFieldException(MessagesExceptions s) {
        super(s.getMessage());
    }
}
