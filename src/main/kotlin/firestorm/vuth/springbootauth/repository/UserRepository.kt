package firestorm.vuth.springbootauth.repository

import firestorm.vuth.springbootauth.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository: JpaRepository<User, UUID> {
    fun findByUsername(username: String?): User?
    fun existsByUsername(username: String): Boolean

    @Query("""
        select u from User u
        left join fetch u.role r
        left join fetch r.permissions
        where u.id = :id
    """)
    fun findByIdWithRole(id: UUID): User?
}