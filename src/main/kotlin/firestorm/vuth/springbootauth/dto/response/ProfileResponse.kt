package firestorm.vuth.springbootauth.dto.response

data class ProfileResponse(
    val username: String?,
    val roleName: String?,
    val permissions: List<String>
)
