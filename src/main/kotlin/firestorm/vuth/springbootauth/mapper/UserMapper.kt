package firestorm.vuth.springbootauth.mapper

import firestorm.vuth.springbootauth.dto.response.ProfileResponse
import firestorm.vuth.springbootauth.dto.response.UserResponse
import firestorm.vuth.springbootauth.model.User

fun User.toResponse(): UserResponse =
    UserResponse(
        this.id,
        this.username,
        this.role?.toResponse(),
    )

fun List<User>.toResponse(): List<UserResponse> =
    this.map { it.toResponse() }

fun User.toProfileResponse(): ProfileResponse = ProfileResponse(
    username = this.username,
)