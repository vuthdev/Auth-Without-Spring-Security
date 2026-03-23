package firestorm.vuth.springbootauth.dto.request

data class AddPermissionRequest(
    val permissions: Set<String>,
)