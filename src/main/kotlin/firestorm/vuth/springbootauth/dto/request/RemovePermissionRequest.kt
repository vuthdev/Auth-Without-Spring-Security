package firestorm.vuth.springbootauth.dto.request

data class RemovePermissionRequest(
    val permissions: Set<String>,
)
