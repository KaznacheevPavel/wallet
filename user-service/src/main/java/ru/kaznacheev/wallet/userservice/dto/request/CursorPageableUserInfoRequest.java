package ru.kaznacheev.wallet.userservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.kaznacheev.wallet.userservice.validation.constraint.ConfigurableMax;

@RequiredArgsConstructor
@Getter
@Setter
public class CursorPageableUserInfoRequest {

    @NotNull(message = "{validation.pagination.limit.not-null}")
    @Positive(message = "{validation.pagination.limit.positive}")
    @ConfigurableMax(value = "pagination.limit.max", message = "{validation.pagination.limit.max}")
    private Integer limit = 10;

    @Positive(message = "{validation.pagination.cursor.positive}")
    private final Long cursor;

}
