package firestorm.vuth.springbootauth.service

import firestorm.vuth.springbootauth.dto.request.AddPermissionRequest
import firestorm.vuth.springbootauth.dto.request.CreateRoleRequest
import firestorm.vuth.springbootauth.dto.request.UpdateRoleRequest
import firestorm.vuth.springbootauth.dto.response.RoleResponse
import java.util.UUID

interface RoleService {
    fun createRole(request: CreateRoleRequest): RoleResponse
    fun addPermissionToRole(roleName: String, request: AddPermissionRequest): RoleResponse
    fun deleteById(id: UUID)
    fun findAll(): List<RoleResponse>
    fun getAllRolePermissions(roleName: String): RoleResponse
    fun updateRole(request: UpdateRoleRequest): RoleResponse
}