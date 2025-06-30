package ru.kaznacheev.wallet.common.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends BasicException {

    public AccessDeniedException(String detail) {
        super(HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN.value(), detail);
    }

}
