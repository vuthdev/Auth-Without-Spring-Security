package firestorm.vuth.springbootauth.annotation.validator

import firestorm.vuth.springbootauth.annotation.IsNotEmptyString
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class IsNotEmptyStringValidator: ConstraintValidator<IsNotEmptyString, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        return !value.isNullOrEmpty()
    }
}