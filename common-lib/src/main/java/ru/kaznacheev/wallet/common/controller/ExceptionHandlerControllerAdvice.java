package ru.kaznacheev.wallet.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kaznacheev.wallet.common.dto.response.ExceptionResponse;
import ru.kaznacheev.wallet.common.dto.response.FieldIssue;
import ru.kaznacheev.wallet.common.exception.BaseException;
import ru.kaznacheev.wallet.common.exception.ConflictException;
import ru.kaznacheev.wallet.common.exception.ExceptionData;
import ru.kaznacheev.wallet.common.exception.ExceptionDetail;
import ru.kaznacheev.wallet.common.exception.ExceptionStatus;
import ru.kaznacheev.wallet.common.exception.NotFoundException;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Контроллер для обработки возникших исключений.
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerControllerAdvice {

    private final Clock clock;

    /**
     * Обрабатывает {@link NotFoundException}.
     *
     * @param exception Возникшее исключение
     * @return {@link ExceptionResponse} с информацией о возникшем исключении
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(NotFoundException exception, HttpServletRequest request) {
        return createResponse(exception, request);
    }

    /**
     * Обрабатывает {@link ConflictException}.
     *
     * @param exception Возникшее исключение
     * @return {@link ExceptionResponse} с информацией о возникшем исключении
     */
    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleConflictException(ConflictException exception, HttpServletRequest request) {
        return createResponse(exception, request);
    }

    /**
     * Обрабатывает {@link MethodArgumentNotValidException}.
     *
     * @param exception Возникшее исключение
     * @return {@link ExceptionResponse} с информацией о возникшем исключении
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                   HttpServletRequest request) {
        List<FieldIssue> fieldIssues = new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(fieldError -> fieldIssues.add(FieldIssue.builder()
                        .field(fieldError.getField())
                        .issue(fieldError.getDefaultMessage())
                        .rejectedValue(fieldError.getRejectedValue())
                        .build()));
        return ExceptionResponse.builder()
                .status(ExceptionStatus.VALIDATION_ERROR.getStatus())
                .title(ExceptionStatus.VALIDATION_ERROR.getTitle())
                .detail(ExceptionDetail.VALIDATION_ERROR.format())
                .data(Map.of(ExceptionData.VALIDATION_ERROR_MESSAGES.getTitle(), fieldIssues))
                .timestamp(Instant.now(clock))
                .path(request.getRequestURI())
                .build();
    }

    private ExceptionResponse createResponse (BaseException exception, HttpServletRequest request) {
        return ExceptionResponse.builder()
                .status(exception.getStatus())
                .title(exception.getTitle())
                .detail(exception.getDetail())
                .timestamp(Instant.now(clock))
                .path(request.getRequestURI())
                .data(exception.getData())
                .build();
    }

}
