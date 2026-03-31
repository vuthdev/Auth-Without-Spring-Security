package firestorm.vuth.springbootauth.service.impl

import firestorm.vuth.springbootauth.dto.request.AuthRequest
import firestorm.vuth.springbootauth.dto.request.CreateUserRequest
import firestorm.vuth.springbootauth.dto.response.LoginResponse
import firestorm.vuth.springbootauth.dto.response.UserResponse
import firestorm.vuth.springbootauth.exception.NotFoundException
import firestorm.vuth.springbootauth.exception.UnauthorizedException
import firestorm.vuth.springbootauth.mapper.toResponse
import firestorm.vuth.springbootauth.model.User
import firestorm.vuth.springbootauth.repository.RoleRepository
import firestorm.vuth.springbootauth.repository.UserRepository
import firestorm.vuth.springbootauth.security.JwtService
import firestorm.vuth.springbootauth.service.UserService
import firestorm.vuth.springbootauth.context.AuthContext
import firestorm.vuth.springbootauth.dto.request.AssignRoleRequest
import firestorm.vuth.springbootauth.dto.response.ProfileResponse
import firestorm.vuth.springbootauth.mapper.toProfileResponse
import firestorm.vuth.springbootauth.utils.LogUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserServiceImpl(
    private val userRepo: UserRepository,
    private val roleRepo: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authContext: AuthContext
): UserService {
    override fun login(request: AuthRequest): LoginResponse {
        val user = userRepo.findByUsername(request.username)
            ?: throw NotFoundException("username not found")

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw UnauthorizedException("incorrect password")
        }

        return LoginResponse(
            accessToken = jwtService.generateAccessToken(user)
        )
    }

    override fun register(request: AuthRequest) {
        if (userRepo.existsByUsername(request.username)) {
            throw UnauthorizedException("User already exists")
        }

        val defaultRole = roleRepo.findByRoleName("user")
            ?: throw NotFoundException("role not found")

        val user = User(
            username = request.username,
            password = passwordEncoder.encode(request.password),
            role = defaultRole
        )

        userRepo.save(user)
    }

    override fun viewProfile(): ProfileResponse {
        val user = authContext.getCurrentUser()

        LogUtil.logJson("${user.username} viewed profile", user.toProfileResponse())
        return user.toProfileResponse()
    }

    override fun viewUserProfile(userId: UUID): ProfileResponse {
        val target = userRepo.findById(userId)
            .orElseThrow { NotFoundException("user not found") }
        val viewer = authContext.getCurrentUser()
        val targetProfile = target.toProfileResponse()

        LogUtil.logJson("${viewer.username} viewed ${target.username}'s profile", targetProfile)
        return targetProfile
    }

    override fun createUser(request: CreateUserRequest) {
        if (userRepo.existsByUsername(request.username)) {
            throw UnauthorizedException("User already exists")
        }

        val role = roleRepo.findByRoleName(request.role)
            ?: throw NotFoundException("role not found")

        val user = User(
            username = request.username,
            password = passwordEncoder.encode(request.password),
            role = role
        )

        userRepo.save(user)
    }

    override fun getAll(): List<UserResponse> {
        val users = userRepo.findAll().toResponse()
        LogUtil.logJson("${userRepo.count()} users found", users)
        return users
    }

    override fun deleteUser(id: UUID) =
        userRepo.deleteById(id)

    override fun assignRole(userId: UUID, request: AssignRoleRequest) {
        val user = userRepo.findById(userId)
            .orElseThrow { NotFoundException("user not found") }

        request.roleName.let {
            user.role = roleRepo.findByRoleName(it)
                ?: throw NotFoundException("role not found")
        }

        userRepo.save(user)
    }
}