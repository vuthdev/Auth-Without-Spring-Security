package firestorm.vuth.springbootauth.service

import firestorm.vuth.springbootauth.dto.req.CreateRoleRequest
import firestorm.vuth.springbootauth.dto.req.UpdateRoleRequest
import firestorm.vuth.springbootauth.dto.res.RoleResponse

interface RoleService {
    fun createRole(request: CreateRoleRequest): RoleResponse
    fun findAll(): List<RoleResponse>
    fun updateRole(request: UpdateRoleRequest): RoleResponse
}