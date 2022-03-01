package com.getir.readingisgood.util;

import org.apache.commons.validator.routines.EmailValidator;

public class EmailUtil {

    public static boolean isValidMail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
