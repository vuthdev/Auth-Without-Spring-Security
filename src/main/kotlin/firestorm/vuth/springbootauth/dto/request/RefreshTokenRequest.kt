package firestorm.vuth.springbootauth.dto.request

import firestorm.vuth.springbootauth.annotation.IsNotEmptyString

data class RefreshTokenRequest(
    @field:IsNotEmptyString
    val refreshToken: String
)
