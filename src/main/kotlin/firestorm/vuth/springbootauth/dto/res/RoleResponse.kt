package firestorm.vuth.springbootauth.dto.res

data class RoleResponse(
    val roleName: String?,
    val permissions: List<PermissionResponse>
)
