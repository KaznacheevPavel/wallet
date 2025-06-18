package ru.kaznacheev.wallet.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BasicException extends RuntimeException{

    private final String title;

    private final int status;

    private final String detail;

}
