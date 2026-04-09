package firestorm.vuth.springbootauth.dto.request

import firestorm.vuth.springbootauth.annotation.IsNotEmptyString
import java.util.UUID

data class CreateRoleRequest(
    @field:IsNotEmptyString
    val roleName: String,
    val permission: List<UUID> = emptyList()
)
