package firestorm.vuth.springbootauth.dto.request

import java.util.UUID

data class UpdateRoleRequest(
    val roleName: String,
    val permissions: List<UUID>
)
