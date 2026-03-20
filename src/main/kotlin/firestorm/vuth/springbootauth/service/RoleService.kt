package firestorm.vuth.springbootauth.service

import firestorm.vuth.springbootauth.dto.`request\`.CreateRoleRequest
import firestorm.vuth.springbootauth.dto.`request\`.UpdateRoleRequest
import firestorm.vuth.springbootauth.dto.response.RoleResponse

interface RoleService {
    fun createRole(request: CreateRoleRequest): RoleResponse
    fun findAll(): List<RoleResponse>
    fun updateRole(request: UpdateRoleRequest): RoleResponse
}