package ru.kaznacheev.wallet.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.core.env.PropertyResolver;
import ru.kaznacheev.wallet.common.validation.constraint.ConfigurableDigits;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class ConfigurableDigitsValidator implements ConstraintValidator<ConfigurableDigits, CharSequence> {

    private final PropertyResolver propertyResolver;
    private int maxIntegerLength;
    private int maxFractionLength;

    @Override
    public void initialize(ConfigurableDigits constraintAnnotation) {
        String maxIntegerProperty = constraintAnnotation.integer();
        if (!maxIntegerProperty.isBlank()) {
            maxIntegerLength = propertyResolver.getRequiredProperty(maxIntegerProperty, Integer.class);
        }
        String maxFractionProperty = constraintAnnotation.fraction();
        if (!maxFractionProperty.isBlank()) {
            maxFractionLength = propertyResolver.getRequiredProperty(maxFractionProperty, Integer.class);
        }
    }

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
        if (charSequence == null) {
            return true;
        } else {
            BigDecimal bigNum = getBigDecimalValue(charSequence);
            if (bigNum == null) {
                HibernateConstraintValidatorContext hibernateConstraintValidatorContext =
                        constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class);
                hibernateConstraintValidatorContext.addMessageParameter("integer", maxIntegerLength);
                hibernateConstraintValidatorContext.addMessageParameter("fraction", maxFractionLength);
                return false;
            }
            int integerPartLength = bigNum.precision() - bigNum.scale();
            int fractionPartLength = Math.max(bigNum.scale(), 0);
            if (maxIntegerLength < integerPartLength || maxFractionLength < fractionPartLength) {
                HibernateConstraintValidatorContext hibernateConstraintValidatorContext =
                        constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class);
                hibernateConstraintValidatorContext.addMessageParameter("integer", maxIntegerLength);
                hibernateConstraintValidatorContext.addMessageParameter("fraction", maxFractionLength);
                return false;
            }
            return true;
        }
    }


    private BigDecimal getBigDecimalValue(CharSequence charSequence) {
        try {
            return new BigDecimal(charSequence.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
