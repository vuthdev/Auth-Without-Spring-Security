package firestorm.vuth.springbootauth.dto.request

import java.util.UUID

data class CreateRoleRequest(
    val roleName: String,
    val permission: List<UUID> = emptyList()
)
