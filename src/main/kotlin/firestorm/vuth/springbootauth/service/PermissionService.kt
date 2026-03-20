package firestorm.vuth.springbootauth.service

import firestorm.vuth.springbootauth.dto.req.CreatePermissionRequest
import firestorm.vuth.springbootauth.dto.res.PermissionResponse
import firestorm.vuth.springbootauth.model.Permission

interface PermissionService {
    fun createPermissions(request: CreatePermissionRequest): PermissionResponse
    fun findAll(): List<PermissionResponse>
}