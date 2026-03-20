package firestorm.vuth.springbootauth.dto.req

import java.util.UUID

data class UpdateRoleRequest(
    val roleName: String? = null,
    val permissions: List<UUID>? = null
)
