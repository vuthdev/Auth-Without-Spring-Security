package firestorm.vuth.springbootauth.mapper

import firestorm.vuth.springbootauth.dto.res.ProfileResponse
import firestorm.vuth.springbootauth.dto.res.UserResponse
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