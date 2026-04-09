package firestorm.vuth.springbootauth.annotation

import firestorm.vuth.springbootauth.annotation.validator.IsNotEmptyStringValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [IsNotEmptyStringValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class IsNotEmptyString(
    val message: String = "can not be null or empty",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
