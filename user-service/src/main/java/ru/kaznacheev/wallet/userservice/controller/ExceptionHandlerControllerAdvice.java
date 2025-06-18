package ru.kaznacheev.wallet.userservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kaznacheev.wallet.userservice.dto.response.ExceptionResponse;
import ru.kaznacheev.wallet.userservice.exception.BasicException;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(BasicException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(BasicException exception, HttpServletRequest request) {
        ExceptionResponse response = ExceptionResponse.builder()
                .title(exception.getTitle())
                .status(exception.getStatus())
                .detail(exception.getDetail())
                .instance(request.getRequestURI())
                .build();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(exception.getStatus()));
    }

}
