package firestorm.vuth.springbootauth.repository

import firestorm.vuth.springbootauth.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface RoleRepository: JpaRepository<Role, UUID> {
    fun findByRoleName(roleName: String?): Role?
    fun existsByRoleName(roleName: String?): Boolean
}