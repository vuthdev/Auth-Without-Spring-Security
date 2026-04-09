package firestorm.vuth.springbootauth.dto.request

import firestorm.vuth.springbootauth.annotation.IsNotEmptyString

data class AddPermissionRequest(
    @field:IsNotEmptyString
    val permissions: Set<String>,
)