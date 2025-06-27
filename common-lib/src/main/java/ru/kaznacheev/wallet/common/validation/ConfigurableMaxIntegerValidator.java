package ru.kaznacheev.wallet.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.core.env.PropertyResolver;
import ru.kaznacheev.wallet.common.validation.constraint.ConfigurableMax;

import java.util.Objects;

@RequiredArgsConstructor
public class ConfigurableMaxIntegerValidator implements ConstraintValidator<ConfigurableMax, Integer> {

    private final PropertyResolver propertyResolver;
    private int maxValue = Integer.MAX_VALUE;

    @Override
    public void initialize(ConfigurableMax constraintAnnotation) {
        String valueProperty = constraintAnnotation.value();
        if (!valueProperty.isBlank()) {
            maxValue = propertyResolver.getRequiredProperty(valueProperty, Integer.class);
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return true;
        }
        if (value > maxValue) {
            HibernateConstraintValidatorContext hibernateConstraintValidatorContext =
                    constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class);
            hibernateConstraintValidatorContext.addMessageParameter("max", maxValue);
            return false;
        }
        return true;
    }

}
