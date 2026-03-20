package firestorm.vuth.springbootauth.service

import firestorm.vuth.springbootauth.dto.`request\`.CreatePermissionRequest
import firestorm.vuth.springbootauth.dto.response.PermissionResponse

interface PermissionService {
    fun createPermissions(request: CreatePermissionRequest): PermissionResponse
    fun findAll(): List<PermissionResponse>
}