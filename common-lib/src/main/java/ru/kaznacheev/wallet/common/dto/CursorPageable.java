package ru.kaznacheev.wallet.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Параметры для запроса с курсорной пагинацией.
 */
@AllArgsConstructor
@Getter
public class CursorPageable {

    /**
     * Количество элементов на "странице".
     */
    private final Integer limit;

    /**
     * Идентификатор последней записи на текущей "странице".
     */
    private final Long cursor;

}
