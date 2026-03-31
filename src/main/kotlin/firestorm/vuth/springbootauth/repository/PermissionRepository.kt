package firestorm.vuth.springbootauth.repository

import firestorm.vuth.springbootauth.model.Permission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PermissionRepository: JpaRepository<Permission, UUID> {
    fun findByPermissionNameIn(permissionName: Set<String>): List<Permission>?

    @Query(
        """
            select exists (
                select 1 from permissions p
                join role_permissions rp ON p.id = rp.permission_id
                where p.permission_name in :permissions and rp.role_id = :roleId
            )
        """,
        nativeQuery = true
    )
    fun existsByPermissionAndRole(permissions: List<String>, roleId: UUID?): Int
}