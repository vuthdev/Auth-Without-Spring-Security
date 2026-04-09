package firestorm.vuth.springbootauth.dto.request

import firestorm.vuth.springbootauth.annotation.IsNotEmptyString

data class CreatePermissionRequest(
    @field:IsNotEmptyString
    val permissionName: String,
)
