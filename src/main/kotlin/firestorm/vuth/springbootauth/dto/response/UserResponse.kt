package firestorm.vuth.springbootauth.dto.response

import java.util.UUID

data class UserResponse(
    val id: UUID?,
    val username: String?,
    val role: RoleResponse?,
)
