package firestorm.vuth.springbootauth.aspect

import firestorm.vuth.springbootauth.annotation.RequiresRole
import firestorm.vuth.springbootauth.exception.ForbiddenException
import firestorm.vuth.springbootauth.context.AuthContext
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Aspect
@Component
class RoleCheckAspect(
    private val authContext: AuthContext
){
    @Before("@annotation(requiresRole)")
    fun checkRole(requiresRole: RequiresRole) {
        val principal = authContext.getCurrentUser()

        if (principal.role?.roleName !in requiresRole.roles) {
            throw ForbiddenException("Forbidden: required role is ${requiresRole.roles.toList()}")
        }
    }
}