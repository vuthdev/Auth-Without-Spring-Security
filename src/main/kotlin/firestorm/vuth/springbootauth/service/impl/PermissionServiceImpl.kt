package firestorm.vuth.springbootauth.service.impl

import firestorm.vuth.springbootauth.dto.req.CreatePermissionRequest
import firestorm.vuth.springbootauth.dto.res.PermissionResponse
import firestorm.vuth.springbootauth.mapper.toResponse
import firestorm.vuth.springbootauth.model.Permission
import firestorm.vuth.springbootauth.repository.PermissionRepository
import firestorm.vuth.springbootauth.service.PermissionService
import org.springframework.stereotype.Service

@Service
class PermissionServiceImpl(
    private val permissionRepository: PermissionRepository
): PermissionService {
    override fun createPermissions(request: CreatePermissionRequest): PermissionResponse {
        val permission = Permission(
            permissionName = request.permissionName
        )

        return permissionRepository.save(permission).toResponse()
    }

    override fun findAll(): List<PermissionResponse> {
        return permissionRepository.findAll().toResponse()
    }
}