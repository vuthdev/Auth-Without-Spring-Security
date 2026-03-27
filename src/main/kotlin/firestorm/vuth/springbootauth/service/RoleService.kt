package firestorm.vuth.springbootauth.service

import firestorm.vuth.springbootauth.dto.request.AddPermissionRequest
import firestorm.vuth.springbootauth.dto.request.CreateRoleRequest
import firestorm.vuth.springbootauth.dto.request.RemovePermissionRequest
import firestorm.vuth.springbootauth.dto.request.UpdateRoleRequest
import firestorm.vuth.springbootauth.dto.response.RoleResponse
import java.util.UUID

interface RoleService {
    fun createRole(request: CreateRoleRequest)
    fun addPermissionToRole(id: UUID, request: AddPermissionRequest)
    fun removePermissionFromRole(id: UUID, request: RemovePermissionRequest)
    fun deleteById(id: UUID)
    fun findAll(): List<RoleResponse>
    fun getAllRolePermissions(id: UUID): RoleResponse
    fun updateRole(request: UpdateRoleRequest)
}