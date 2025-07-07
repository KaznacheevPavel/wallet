package ru.kaznacheev.wallet.common.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.kaznacheev.wallet.common.validation.ConfigurableDigitsValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ConfigurableDigits.List.class)
@Documented
@Constraint(validatedBy = {ConfigurableDigitsValidator.class})
public @interface ConfigurableDigits {

    String message() default "{jakarta.validation.constraints.Digits.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String integer();

    String fraction();

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        ConfigurableDigits[] value();
    }

}
