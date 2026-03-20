package firestorm.vuth.springbootauth.dto.res

import firestorm.vuth.springbootauth.model.Role
import java.util.UUID

data class UserResponse(
    val id: UUID?,
    val username: String?,
    val role: RoleResponse?,
)
