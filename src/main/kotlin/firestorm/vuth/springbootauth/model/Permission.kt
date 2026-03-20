package firestorm.vuth.springbootauth.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "permissions")
class Permission (
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "permission_name", nullable = false)
    var permissionName: String? = null,

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    var roles: Set<Role> = setOf(),
)