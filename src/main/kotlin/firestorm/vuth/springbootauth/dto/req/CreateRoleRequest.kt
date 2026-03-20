package firestorm.vuth.springbootauth.dto.req

import java.util.UUID

data class CreateRoleRequest(
    val roleName: String,
    val permission: List<UUID> = emptyList()
)
