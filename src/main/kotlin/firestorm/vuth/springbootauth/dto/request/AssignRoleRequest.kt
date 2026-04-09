package firestorm.vuth.springbootauth.dto.request

import firestorm.vuth.springbootauth.annotation.IsNotEmptyString

data class AssignRoleRequest(
    @field:IsNotEmptyString
    var roleName : String
)
