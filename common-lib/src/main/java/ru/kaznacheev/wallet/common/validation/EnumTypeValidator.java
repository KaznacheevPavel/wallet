package ru.kaznacheev.wallet.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.kaznacheev.wallet.common.validation.constraint.EnumType;

import java.util.Arrays;
import java.util.Objects;

public class EnumTypeValidator implements ConstraintValidator<EnumType, String> {

    private Enum<?>[] values;

    @Override
    public void initialize(EnumType constraintAnnotation) {
        values = constraintAnnotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return false;
        }
        return Arrays.stream(values)
                .anyMatch(enumValue -> enumValue.name().equals(value));
    }

}
