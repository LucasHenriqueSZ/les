package com.les.vest_fut.exceptions;

import com.les.vest_fut.Enums.MessagesExceptions;

public class UniqueFieldException extends CustomException {
    public UniqueFieldException(MessagesExceptions s) {
        super(s.getMessage());
    }
}
