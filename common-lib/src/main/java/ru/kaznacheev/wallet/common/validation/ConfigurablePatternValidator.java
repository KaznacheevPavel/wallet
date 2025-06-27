package ru.kaznacheev.wallet.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.PropertyResolver;
import ru.kaznacheev.wallet.common.validation.constraint.ConfigurablePattern;

import java.util.regex.Matcher;

@RequiredArgsConstructor
public class ConfigurablePatternValidator implements ConstraintValidator<ConfigurablePattern, String> {

    private final PropertyResolver propertyResolver;
    private java.util.regex.Pattern pattern;
    private String regexp;

    @Override
    public void initialize(ConfigurablePattern constraintAnnotation) {
        String regexpProperty = constraintAnnotation.regexp();
        if (!regexpProperty.isBlank()) {
            regexp = propertyResolver.getRequiredProperty(regexpProperty, String.class);
        }
        pattern = java.util.regex.Pattern.compile(regexp);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        Matcher m = pattern.matcher(value);
        return m.matches();
    }

}
