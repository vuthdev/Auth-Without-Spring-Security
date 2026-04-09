package firestorm.vuth.springbootauth.dto.request

import firestorm.vuth.springbootauth.annotation.IsNotEmptyString
import jakarta.validation.constraints.NotBlank

data class AuthRequest(
    @field:IsNotEmptyString
    val username: String,

    @field:IsNotEmptyString
    val password: String
)