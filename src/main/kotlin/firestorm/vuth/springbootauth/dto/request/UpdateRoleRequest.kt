package firestorm.vuth.springbootauth.dto.request

import firestorm.vuth.springbootauth.annotation.IsNotEmptyString
import java.util.UUID

data class UpdateRoleRequest(
    @field:IsNotEmptyString
    val roleName: String,
    val permissions: List<UUID>
)
