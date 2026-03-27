package firestorm.vuth.springbootauth.aspect

import firestorm.vuth.springbootauth.annotation.RequiresPermission
import firestorm.vuth.springbootauth.exception.ForbiddenException
import firestorm.vuth.springbootauth.repository.PermissionRepository
import firestorm.vuth.springbootauth.context.AuthContext
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Aspect
@Component
class PermissionCheckAspect(
    private val permissionRepository: PermissionRepository,
    private val authContext: AuthContext
) {
    @Before("@annotation(requirePermission)")
    fun checkPermission(requirePermission: RequiresPermission) {
        val user = authContext.getCurrentUser()

        val hasPermission = requirePermission.permission.any {
            permissionRepository.existsByPermissionNameAndRoles(it, user.role)
        }

        if (!hasPermission) {
            throw ForbiddenException("Forbidden: User role ${user.role?.roleName} doesn't have ${requirePermission.permission.toList()} permission!")
        }
    }
}