package ru.kaznacheev.wallet.common.dto;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.kaznacheev.wallet.common.constraint.group.AdvancedGroup;

/**
 * Параметры для запроса с курсорной пагинацией.
 */
@RequiredArgsConstructor
@Getter
@Setter
@GroupSequence({CursorPageable.class, AdvancedGroup.class})
public class CursorPageable {

    /**
     * Количество элементов на "странице".
     */
    @NotNull(message = "Количество выводимых элементов обязательно")
    @Positive(message = "Количество выводимых элементов должно быть больше нуля", groups = AdvancedGroup.class)
    @Max(message = "Количество выводимых элементов не должно быть больше 25", value = 25, groups = AdvancedGroup.class)
    private Integer limit = 10;

    /**
     * Идентификатор последней записи на текущей "странице".
     */
    @NotNull(message = "Курсор обязателен")
    @Positive(message = "Курсор должен быть больше нуля", groups = AdvancedGroup.class)
    private final Long cursor;

}
