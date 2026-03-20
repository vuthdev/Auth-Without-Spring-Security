package firestorm.vuth.springbootauth.aspect

import firestorm.vuth.springbootauth.annotation.RequiresRole
import firestorm.vuth.springbootauth.exception.ForbiddenException
import firestorm.vuth.springbootauth.context.AuthContext
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class RoleCheckAspect(
    private val authContext: AuthContext
){
    private val log = LoggerFactory.getLogger(javaClass)

    @Before("@annotation(requiresRole)")
    fun checkRole(requiresRole: RequiresRole) {

        val principal = authContext.getCurrentUser()

        if (principal.role != requiresRole.roles) {
            throw ForbiddenException("Forbidden: ${requiresRole.roles}")
        }
    }
}