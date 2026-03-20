package firestorm.vuth.springbootauth.service.impl

import firestorm.vuth.springbootauth.dto.req.CreateRoleRequest
import firestorm.vuth.springbootauth.dto.req.UpdateRoleRequest
import firestorm.vuth.springbootauth.dto.res.RoleResponse
import firestorm.vuth.springbootauth.exception.NotFoundException
import firestorm.vuth.springbootauth.mapper.toResponse
import firestorm.vuth.springbootauth.model.Role
import firestorm.vuth.springbootauth.repository.PermissionRepository
import firestorm.vuth.springbootauth.repository.RoleRepository
import firestorm.vuth.springbootauth.service.RoleService
import org.springframework.stereotype.Service

@Service
class RoleServiceImpl(
    private val roleRepository: RoleRepository,
    private val permissionRepository: PermissionRepository
): RoleService {
    override fun createRole(request: CreateRoleRequest): RoleResponse {
        val permission = permissionRepository.findAllById(request.permission).toHashSet()

        val role = Role(
            roleName = request.roleName
        )
        role.permissions = permission

        return roleRepository.save(role).toResponse()
    }

    override fun findAll(): List<RoleResponse> {
        return roleRepository.findAll().toResponse()
    }

    override fun updateRole(request: UpdateRoleRequest): RoleResponse {
        val role = roleRepository.findByRoleName(request.roleName)
            ?: throw NotFoundException("Role ${request.roleName} not found")

        request.roleName?.let { role.roleName = it }
        request.permissions?.let {
            role.permissions = permissionRepository.findAllById(it).toHashSet()
        }

        return roleRepository.save(role).toResponse()
    }
}