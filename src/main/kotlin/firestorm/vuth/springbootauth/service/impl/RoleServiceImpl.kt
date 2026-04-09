package firestorm.vuth.springbootauth.service.impl

import firestorm.vuth.springbootauth.dto.request.AddPermissionRequest
import firestorm.vuth.springbootauth.dto.request.CreateRoleRequest
import firestorm.vuth.springbootauth.dto.request.RemovePermissionRequest
import firestorm.vuth.springbootauth.dto.request.UpdateRoleRequest
import firestorm.vuth.springbootauth.dto.response.RoleResponse
import firestorm.vuth.springbootauth.exception.AlreadyExistsException
import firestorm.vuth.springbootauth.exception.NotFoundException
import firestorm.vuth.springbootauth.mapper.toResponse
import firestorm.vuth.springbootauth.model.Role
import firestorm.vuth.springbootauth.repository.PermissionRepository
import firestorm.vuth.springbootauth.repository.RoleRepository
import firestorm.vuth.springbootauth.service.RoleService
import firestorm.vuth.springbootauth.common.util.LogUtil
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class RoleServiceImpl(
    private val roleRepository: RoleRepository,
    private val permissionRepository: PermissionRepository
): RoleService {
    override fun createRole(request: CreateRoleRequest) {
        LogUtil.info("Creating role $request")

        if (roleRepository.existsByRoleName(request.roleName)) {
            LogUtil.error("Role $request.roleName already exists.")
            throw AlreadyExistsException("Role $request.roleName already exists")
        }

        val permission = permissionRepository.findAllById(request.permission).toHashSet()

        val missingId = request.permission - permission.map { it.id }.toHashSet()
        if (missingId.isNotEmpty()) {
            LogUtil.error("Permission not found: $missingId")
            throw NotFoundException("Permission not found: $missingId")
        }

        val role = Role(roleName = request.roleName)
        role.permissions = permission

        roleRepository.save(role)
        LogUtil.info("Created role $role")
    }

    override fun addPermissionToRole(
        id: UUID,
        request: AddPermissionRequest
    ) {
        LogUtil.info("Adding permission to role $request")
        val role = roleRepository.findById(id)
            .orElseThrow {
                LogUtil.error("Role with id $id not found")
                NotFoundException("Role not found with id $id")
            }

        val found = permissionRepository.findByPermissionNameIn(request.permissions)

        if (found?.size != request.permissions.size) {
            val foundName = found?.map { it.permissionName?.uppercase() }?.toSet()
            val invalid = request.permissions - foundName
            LogUtil.error("Unknown permission: $invalid")
            throw NotFoundException("Unknown permission: $invalid")
        }

        val existingNames = role.permissions.map { it.permissionName }.toSet()
        val alreadyAssigned = request.permissions.intersect(existingNames)

        if (alreadyAssigned.isNotEmpty()) {
            LogUtil.error("Permission already assigned to this role: $alreadyAssigned")
            throw AlreadyExistsException("Permission already assigned to this role: $alreadyAssigned")
        }

        role.permissions += found.toSet()
        roleRepository.save(role)
        LogUtil.info("added permission to this role $role")
    }

    override fun removePermissionFromRole(
        id: UUID,
        request: RemovePermissionRequest
    ) {
        LogUtil.info("Removing role $request")
        val role = roleRepository.findById(id)
            .orElseThrow {
                LogUtil.error("Role not found with id $id")
                NotFoundException("Role not found with id $id")
            }

        val found = permissionRepository.findByPermissionNameIn(request.permissions)
        if (found?.size != request.permissions.size) {
            val foundName = found?.map { it.permissionName?.uppercase() }?.toSet()
            val invalid = request.permissions - foundName
            LogUtil.error("Unknown permission: $invalid")
            throw NotFoundException("Unknown permission: $invalid")
        }

        role.permissions -= found.toSet()
        roleRepository.save(role)
        LogUtil.info("Removed role $id")
    }

    override fun deleteById(id: UUID) {
        LogUtil.info("Deleting role $id")
        if (!roleRepository.existsById(id)) {
            LogUtil.error("Role with id $id does not exist")
            throw AlreadyExistsException("Role with id $id does not exist")
        }

        roleRepository.deleteById(id)
        LogUtil.info("Deleted role $id")
    }

    override fun findAll(): List<RoleResponse> {
        LogUtil.info("Retrieving all roles")
        val roleList = roleRepository.findAll().toResponse()

        if (roleList.isEmpty()) {
            LogUtil.info("No roles found")
            return roleList
        }

        LogUtil.info("Retrieved ${roleList.size} roles")
        return roleList
    }

    override fun getAllRolePermissions(id: UUID): RoleResponse {
        LogUtil.info("Retrieving all role")
        val role = roleRepository.findById(id)
            .orElseThrow {
                LogUtil.error("Role not found with id: $id")
                NotFoundException("Role not found with id $id")
            }
        val roleList = role.toResponse()

        LogUtil.info("${role.permissions.size} permissions found")
        return roleList
    }

    override fun updateRole(request: UpdateRoleRequest) {
        LogUtil.info("Updating role $request")
        val role = roleRepository.findByRoleName(request.roleName)
            ?: run {
                LogUtil.error("Role with name ${request.roleName} does not exist")
                throw NotFoundException("Role ${request.roleName} not found")
            }

        val permissions = permissionRepository.findAllById(request.permissions).toHashSet()
        val missingId = request.permissions - permissions.map { it.id }.toHashSet()
        if (missingId.isNotEmpty()) {
            LogUtil.error("Permission not found: $missingId")
            throw NotFoundException("Permission not found: $missingId")
        }

        role.roleName = request.roleName
        role.permissions = permissions

        roleRepository.save(role)
        LogUtil.info("Updated role $request")
    }
}