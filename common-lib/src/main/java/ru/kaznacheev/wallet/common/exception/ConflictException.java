package ru.kaznacheev.wallet.common.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends BasicException {

    public ConflictException(String detail) {
        super(HttpStatus.CONFLICT.getReasonPhrase(), HttpStatus.CONFLICT.value(), detail);
    }

}
