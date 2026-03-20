package firestorm.vuth.springbootauth.dto.response

import java.util.UUID

data class PermissionResponse (
    val id: UUID?,
    val permissionName: String?
)