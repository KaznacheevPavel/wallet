package ru.kaznacheev.wallet.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kaznacheev.wallet.common.dto.ExceptionResponse;
import ru.kaznacheev.wallet.common.exception.BaseException;
import ru.kaznacheev.wallet.common.exception.ConflictException;
import ru.kaznacheev.wallet.common.exception.NotFoundException;

/**
 * Контроллер для обработки возникших исключений.
 */
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

    /**
     * Обрабатывает {@link NotFoundException}.
     *
     * @param exception Возникшее исключение
     * @return {@link ExceptionResponse} с информацией о возникшем исключении
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(NotFoundException exception) {
        return createResponse(exception);
    }

    /**
     * Обрабатывает {@link ConflictException}.
     *
     * @param exception Возникшее исключение
     * @return {@link ExceptionResponse} с информацией о возникшем исключении
     */
    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleConflictException(ConflictException exception) {
        return createResponse(exception);
    }

    private ExceptionResponse createResponse (BaseException exception) {
        return ExceptionResponse.builder()
                .status(exception.getStatus())
                .title(exception.getTitle())
                .detail(exception.getDetail())
                .data(exception.getData())
                .build();
    }

}
