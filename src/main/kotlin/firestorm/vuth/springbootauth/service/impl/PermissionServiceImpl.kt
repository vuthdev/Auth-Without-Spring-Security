package firestorm.vuth.springbootauth.service.impl

import firestorm.vuth.springbootauth.dto.request.CreatePermissionRequest
import firestorm.vuth.springbootauth.dto.response.PermissionResponse
import firestorm.vuth.springbootauth.mapper.toResponse
import firestorm.vuth.springbootauth.model.Permission
import firestorm.vuth.springbootauth.repository.PermissionRepository
import firestorm.vuth.springbootauth.service.PermissionService
import firestorm.vuth.springbootauth.utils.LogUtil
import org.aspectj.apache.bcel.classfile.JavaClass
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PermissionServiceImpl(
    private val permissionRepository: PermissionRepository
): PermissionService {
    override fun createPermissions(request: CreatePermissionRequest) {
        val permission = Permission(
            permissionName = request.permissionName
        )

        permissionRepository.save(permission)
    }

    override fun deleteById(id: UUID) {
        permissionRepository.deleteById(id)
    }

    override fun findAll(): List<PermissionResponse> {
        val permissionList = permissionRepository.findAll().toResponse()
        LogUtil.logJson("${permissionList.size} permissions found", permissionList)
        return permissionList
    }
}