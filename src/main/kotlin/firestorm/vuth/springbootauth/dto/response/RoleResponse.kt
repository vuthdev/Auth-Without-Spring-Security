package firestorm.vuth.springbootauth.dto.response

data class RoleResponse(
    val roleName: String?,
    val permissions: List<PermissionResponse>
)
