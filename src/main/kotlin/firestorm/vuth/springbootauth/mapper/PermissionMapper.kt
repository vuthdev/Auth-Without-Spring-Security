package firestorm.vuth.springbootauth.mapper

import firestorm.vuth.springbootauth.dto.res.PermissionResponse
import firestorm.vuth.springbootauth.model.Permission

fun Permission.toResponse(): PermissionResponse =
    PermissionResponse(
        id = this.id,
        permissionName = this.permissionName,
    )

fun List<Permission>.toResponse(): List<PermissionResponse> =
    this.map { it.toResponse() }