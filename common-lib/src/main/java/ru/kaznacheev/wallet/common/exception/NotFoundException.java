package ru.kaznacheev.wallet.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BasicException {

    public NotFoundException(String detail) {
        super(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.value(), detail);
    }

}
