package firestorm.vuth.springbootauth.service.impl

import firestorm.vuth.springbootauth.dto.request.CreatePermissionRequest
import firestorm.vuth.springbootauth.dto.response.PermissionResponse
import firestorm.vuth.springbootauth.exception.AlreadyExistsException
import firestorm.vuth.springbootauth.mapper.toResponse
import firestorm.vuth.springbootauth.model.Permission
import firestorm.vuth.springbootauth.repository.PermissionRepository
import firestorm.vuth.springbootauth.service.PermissionService
import firestorm.vuth.springbootauth.utils.LogUtil
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PermissionServiceImpl(
    private val permissionRepository: PermissionRepository
): PermissionService {
    override fun createPermissions(request: CreatePermissionRequest) {
        LogUtil.info("Creating permission ${request.permissionName}")
        if (permissionRepository.existsByPermissionName(request.permissionName)) {
            LogUtil.error("Permission already exists: ${request.permissionName}")
            throw AlreadyExistsException("Permission already exists: ${request.permissionName}")
        }

        val permission = Permission(
            permissionName = request.permissionName
        )

        permissionRepository.save(permission)
        LogUtil.info("Permission ${permission.permissionName} created")
    }

    override fun deleteById(id: UUID) {
        LogUtil.info("Deleting permission ${id}")

        if (!permissionRepository.existsById(id)) {
            LogUtil.error("Permission does not exist: $id")
            throw AlreadyExistsException("Permission does not exist: $id")
        }

        permissionRepository.deleteById(id)
        LogUtil.info("Permission $id deleted")
    }

    override fun findAll(): List<PermissionResponse> {
        LogUtil.info("Retrieving all permissions")
        val permissionList = permissionRepository.findAll()

        if (permissionList.isEmpty()) {
            LogUtil.info("No permissions found")
            return emptyList()
        }

        LogUtil.info("Found ${permissionList.size} permissions")
        return permissionList.toResponse()
    }
}