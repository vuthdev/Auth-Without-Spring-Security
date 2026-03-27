package firestorm.vuth.springbootauth.service.impl

import firestorm.vuth.springbootauth.dto.request.AddPermissionRequest
import firestorm.vuth.springbootauth.dto.request.CreateRoleRequest
import firestorm.vuth.springbootauth.dto.request.RemovePermissionRequest
import firestorm.vuth.springbootauth.dto.request.UpdateRoleRequest
import firestorm.vuth.springbootauth.dto.response.RoleResponse
import firestorm.vuth.springbootauth.exception.NotFoundException
import firestorm.vuth.springbootauth.mapper.toResponse
import firestorm.vuth.springbootauth.model.Role
import firestorm.vuth.springbootauth.repository.PermissionRepository
import firestorm.vuth.springbootauth.repository.RoleRepository
import firestorm.vuth.springbootauth.service.RoleService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Service
class RoleServiceImpl(
    private val roleRepository: RoleRepository,
    private val permissionRepository: PermissionRepository
): RoleService {
    override fun createRole(request: CreateRoleRequest) {
        val permission = permissionRepository.findAllById(request.permission).toHashSet()

        val role = Role(
            roleName = request.roleName
        )
        role.permissions = permission

        roleRepository.save(role)
    }

    override fun addPermissionToRole(
        id: UUID,
        request: AddPermissionRequest
    ) {
        val role = roleRepository.findById(id)
            .orElseThrow { NotFoundException("Not found with id $id") }

        val found = permissionRepository.findByPermissionNameIn(request.permissions)

        if (found?.size != request.permissions.size) {
            val foundName = found?.map { it.permissionName?.uppercase() }?.toSet()
            val invalid = request.permissions - foundName
            throw NotFoundException("Unknown permission: $invalid")
        }

        val existingNames = role.permissions.map { it.permissionName }.toSet()
        val alreadyAssigned = request.permissions.intersect(existingNames)

        if (alreadyAssigned.isNotEmpty()) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Permission already assigned to this role: $alreadyAssigned"
            )
        }

        role.permissions += found.toSet()
        roleRepository.save(role)
    }

    override fun removePermissionFromRole(
        id: UUID,
        request: RemovePermissionRequest
    ) {
        val role = roleRepository.findById(id)
            .orElseThrow { NotFoundException("Not found with id $id") }

        val found = permissionRepository.findByPermissionNameIn(request.permissions)
        if (found?.size != request.permissions.size) {
            val foundName = found?.map { it.permissionName?.uppercase() }?.toSet()
            val invalid = request.permissions - foundName
            throw NotFoundException("Unknown permission: $invalid")
        }

        role.permissions -= found.toSet()
        roleRepository.save(role)
    }

    override fun deleteById(id: UUID) {
        roleRepository.deleteById(id)
    }

    override fun findAll(): List<RoleResponse> {
        return roleRepository.findAll().toResponse()
    }

    override fun getAllRolePermissions(id: UUID): RoleResponse {
        val role = roleRepository.findById(id)
            .orElseThrow { NotFoundException("Not found with id $id") }

        return role.toResponse()
    }

    override fun updateRole(request: UpdateRoleRequest) {
        val role = roleRepository.findByRoleName(request.roleName)
            ?: throw NotFoundException("Role ${request.roleName} not found")

        request.roleName?.let { role.roleName = it }
        request.permissions?.let {
            role.permissions = permissionRepository.findAllById(it).toHashSet()
        }

        roleRepository.save(role)
    }
}