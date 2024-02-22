package tech.olatunbosun.wastemanagement.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import tech.olatunbosun.wastemanagement.usermanagement.utility.enums.UserType;

public class EnumValidator implements ConstraintValidator<EnumValue, String> {
    private EnumValue annotation;

    @Override
    public void initialize(EnumValue annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = false;

        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (value.equals(((UserType) enumValue).getUserType()) ||
                        (this.annotation.ignoreCase() && value.equalsIgnoreCase(((UserType) enumValue).getUserType()))) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }
}