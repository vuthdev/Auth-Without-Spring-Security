package firestorm.vuth.springbootauth.mapper

import firestorm.vuth.springbootauth.dto.res.PermissionResponse
import firestorm.vuth.springbootauth.dto.res.RoleResponse
import firestorm.vuth.springbootauth.model.Role

fun Role.toResponse(): RoleResponse = RoleResponse(
    roleName = this.roleName,
    permissions = this.permissions.map { PermissionResponse(it.id, it.permissionName) }
)

fun List<Role>.toResponse(): List<RoleResponse> = this.map { it.toResponse() }