package ru.kaznacheev.wallet.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.core.env.PropertyResolver;
import ru.kaznacheev.wallet.common.validation.constraint.ConfigurableSize;

import java.util.Objects;

@RequiredArgsConstructor
public class ConfigurableSizeStringValidator implements ConstraintValidator<ConfigurableSize, String> {

    private final PropertyResolver propertyResolver;
    private int min = 0;
    private int max = Integer.MAX_VALUE;

    @Override
    public void initialize(ConfigurableSize constraintAnnotation) {
        String minProperty = constraintAnnotation.min();
        String maxProperty = constraintAnnotation.max();
        if (!minProperty.isBlank()) {
            min = propertyResolver.getRequiredProperty(minProperty, Integer.class);
        }
        if (!maxProperty.isBlank()) {
            max = propertyResolver.getRequiredProperty(maxProperty, Integer.class);
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return true;
        }
        int length = value.length();
        if (length < min || length > max) {
            HibernateConstraintValidatorContext hibernateConstraintValidatorContext =
                    constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class);
            hibernateConstraintValidatorContext.addMessageParameter("min", min);
            hibernateConstraintValidatorContext.addMessageParameter("max", max);
            return false;
        }
        return true;
    }

}
