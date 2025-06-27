package ru.kaznacheev.wallet.common.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.kaznacheev.wallet.common.validation.ConfigurablePatternValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ConfigurablePattern.List.class)
@Constraint(validatedBy = {ConfigurablePatternValidator.class})
public @interface ConfigurablePattern {

    String regexp();

    String message() default "{jakarta.validation.constraints.Pattern.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface List {
        ConfigurablePattern[] value();
    }

}
