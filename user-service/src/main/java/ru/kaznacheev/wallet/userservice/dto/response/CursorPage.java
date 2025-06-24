package ru.kaznacheev.wallet.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CursorPage<T, K> {

    private final List<T> data;

    private final K nextCursor;

}
