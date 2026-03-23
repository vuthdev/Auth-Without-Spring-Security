package firestorm.vuth.springbootauth.repository

import firestorm.vuth.springbootauth.model.Permission
import firestorm.vuth.springbootauth.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PermissionRepository: JpaRepository<Permission, UUID> {
    fun findByPermissionNameIn(permissionName: Set<String>): List<Permission>?
    fun existsByPermissionNameAndRoles(permissionName: String, roles: Role?): Boolean
    fun existsByPermissionName(permissionName: String): Boolean
}