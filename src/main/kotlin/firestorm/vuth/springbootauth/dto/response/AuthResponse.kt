package firestorm.vuth.springbootauth.dto.response

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
)
