package firestorm.vuth.springbootauth.dto.`request\`

data class CreateUserRequest(
    val username: String,
    val password: String,
    val role: String
)
