package firestorm.vuth.springbootauth.dto.response

data class RoleResponse(
    val roleId: String?,
    val roleName: String?,
    val permissions: List<PermissionResponse>
)
