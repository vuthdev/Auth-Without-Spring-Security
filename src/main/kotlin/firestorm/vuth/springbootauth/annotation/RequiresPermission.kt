package firestorm.vuth.springbootauth.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequiresPermission(
    vararg val permission: String,
)
