package ru.kaznacheev.wallet.common.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BasicException {

    public UnauthorizedException(String detail) {
        super(HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED.value(), detail);
    }

}
