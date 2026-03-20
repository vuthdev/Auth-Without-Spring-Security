package firestorm.vuth.springbootauth.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequiresRole(
    vararg val roles: String
)