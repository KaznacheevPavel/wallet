package ru.kaznacheev.wallet.common.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.kaznacheev.wallet.common.validation.EnumTypeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumTypeValidator.class)
public @interface EnumType {

    Class<? extends Enum<?>> enumClass();

    String message() default "Неверное значение для перечисления";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
