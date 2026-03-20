package firestorm.vuth.springbootauth.dto.res

import java.util.UUID

data class PermissionResponse (
    val id: UUID?,
    val permissionName: String?
)