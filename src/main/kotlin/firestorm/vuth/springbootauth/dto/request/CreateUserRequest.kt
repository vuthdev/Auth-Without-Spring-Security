package firestorm.vuth.springbootauth.dto.request

import firestorm.vuth.springbootauth.annotation.IsNotEmptyString

data class CreateUserRequest(
    @field:IsNotEmptyString
    val username: String,
    @field:IsNotEmptyString
    val password: String,
    @field:IsNotEmptyString
    val role: String
)
