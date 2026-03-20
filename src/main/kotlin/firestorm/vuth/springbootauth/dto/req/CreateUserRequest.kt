package firestorm.vuth.springbootauth.dto.req

data class CreateUserRequest(
    val username: String,
    val password: String,
    val role: String
)
