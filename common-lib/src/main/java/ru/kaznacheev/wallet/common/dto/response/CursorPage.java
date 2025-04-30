package ru.kaznacheev.wallet.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Постраничный вывод данных с использованием курсорного метода пагинации.
 *
 * @param <T> Тип данных выводимых объектов
 */
@AllArgsConstructor
@Getter
public class CursorPage<T> {

    /**
     * Данные
     */
    private final T data;

    /**
     * Идентификатор последней записи.
     */
    private final Long nextCursor;

}
