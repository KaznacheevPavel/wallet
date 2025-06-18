package ru.kaznacheev.wallet.userservice.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BasicException {

    public NotFoundException(String detail) {
        super(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.value(), detail);
    }

}
