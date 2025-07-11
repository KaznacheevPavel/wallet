package ru.kaznacheev.wallet.common.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BasicException {

    public BadRequestException(String detail) {
        super(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value(), detail);
    }

}
