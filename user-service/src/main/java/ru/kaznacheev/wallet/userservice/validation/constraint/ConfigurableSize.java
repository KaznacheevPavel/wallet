package ru.kaznacheev.wallet.userservice.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.kaznacheev.wallet.userservice.validation.ConfigurableSizeCharArrayValidator;
import ru.kaznacheev.wallet.userservice.validation.ConfigurableSizeValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ConfigurableSize.List.class)
@Constraint(validatedBy = {ConfigurableSizeValidator.class, ConfigurableSizeCharArrayValidator.class})
public @interface ConfigurableSize {

    String message() default "{jakarta.validation.constraints.Size.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String min() default "";

    String max() default "";

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        ConfigurableSize[] value();
    }

}
