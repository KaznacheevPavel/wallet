package ru.kaznacheev.wallet.common.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kaznacheev.wallet.common.dto.ExceptionResponse;
import ru.kaznacheev.wallet.common.exception.BaseException;
import ru.kaznacheev.wallet.common.exception.ConflictException;
import ru.kaznacheev.wallet.common.exception.ExceptionData;
import ru.kaznacheev.wallet.common.exception.ExceptionDetail;
import ru.kaznacheev.wallet.common.exception.ExceptionStatus;
import ru.kaznacheev.wallet.common.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     * Обрабатывает {@link ConstraintViolationException}.
     *
     * @param exception Возникшее исключение
     * @return {@link ExceptionResponse} с информацией о возникшем исключении
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleConstraintViolationException(ConstraintViolationException exception) {
        List<String> constraintMessages = new ArrayList<>(exception.getConstraintViolations().size());
        exception.getConstraintViolations()
                .forEach(constraintViolation ->
                        constraintMessages.add(constraintViolation.getMessage()));
        return ExceptionResponse.builder()
                .status(ExceptionStatus.VALIDATION_ERROR.getStatus())
                .title(ExceptionStatus.VALIDATION_ERROR.getTitle())
                .detail(ExceptionDetail.VALIDATION_ERROR.format())
                .data(Map.of(ExceptionData.VALIDATION_ERROR_MESSAGES.getTitle(), constraintMessages))
                .build();
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
