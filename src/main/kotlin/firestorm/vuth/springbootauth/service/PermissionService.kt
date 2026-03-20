package firestorm.vuth.springbootauth.service

import firestorm.vuth.springbootauth.dto.request.CreatePermissionRequest
import firestorm.vuth.springbootauth.dto.response.PermissionResponse
import java.util.UUID

interface PermissionService {
    fun createPermissions(request: CreatePermissionRequest): PermissionResponse
    fun deleteById(id: UUID)
    fun findAll(): List<PermissionResponse>
}