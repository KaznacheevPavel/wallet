package ru.kaznacheev.wallet.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kaznacheev.wallet.common.dto.response.ExceptionResponse;
import ru.kaznacheev.wallet.common.dto.response.ValidationExceptionResponse;
import ru.kaznacheev.wallet.common.exception.BasicException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerControllerAdvice {

    @Qualifier("commonMessageSource")
    private final MessageSource messageSource;

    @ExceptionHandler(BasicException.class)
    public ResponseEntity<ExceptionResponse> handleBasicException(BasicException exception, HttpServletRequest request) {
        ExceptionResponse response = ExceptionResponse.builder()
                .title(exception.getTitle())
                .status(exception.getStatus())
                .detail(exception.getDetail())
                .instance(request.getRequestURI())
                .build();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(exception.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {
        List<ValidationExceptionResponse.ValidationIssue> validationIssues = new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(fieldError -> validationIssues.add(ValidationExceptionResponse.ValidationIssue.builder()
                                .field(fieldError.getField())
                                .detail(fieldError.getDefaultMessage())
                        .build()));
        return ValidationExceptionResponse.builder()
                .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(messageSource.getMessage("exception.validation-issue",
                        new Object[]{}, Locale.getDefault()))
                .instance(request.getRequestURI())
                .validationIssues(validationIssues)
                .build();
    }

}
