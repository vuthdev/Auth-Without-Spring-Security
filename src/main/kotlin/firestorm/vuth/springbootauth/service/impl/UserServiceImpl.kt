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
import firestorm.vuth.springbootauth.exception.AlreadyExistsException
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
            ?: run {
                LogUtil.error("User not found with username: ${request.username}")
                throw NotFoundException("User not found with username: ${request.username}")
            }

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw UnauthorizedException("incorrect password")
        }

        LogUtil.info("Logging into user ${user.username}")
        val accessToken = jwtService.generateAccessToken(user)
        return LoginResponse(
            accessToken = accessToken
        )
    }

    override fun register(request: AuthRequest) {
        if (userRepo.existsByUsername(request.username)) {
            LogUtil.error("User already exists")
            throw AlreadyExistsException("User already exists")
        }

        val defaultRoleId = UUID.fromString("7a47a0d9-da32-4fc6-8ff3-a11f9ae4071b")
        val defaultRole = roleRepo.findById(defaultRoleId).orElseThrow {
            LogUtil.error("Cannot find default role")
            NotFoundException("role not found")
        }

        val user = User(
            username = request.username,
            password = passwordEncoder.encode(request.password),
            role = defaultRole
        )

        userRepo.save(user)
        LogUtil.info("Registered new user: ${user.username}")
    }

    override fun viewProfile(): ProfileResponse {
        val user = authContext.getCurrentUser()

        LogUtil.info("${user.username} viewed profile", user.toProfileResponse())
        return user.toProfileResponse()
    }

    override fun viewUserProfile(userId: UUID): ProfileResponse {
        val target = userRepo.findById(userId).orElseThrow {
            LogUtil.error("User not found")
            NotFoundException("User not found")
        }
        val viewer = authContext.getCurrentUser()
        val targetProfile = target.toProfileResponse()

        LogUtil.info("${viewer.username} viewed ${target.username}'s profile")
        return targetProfile
    }

    override fun createUser(request: CreateUserRequest) {
        LogUtil.info("creating user ${request.username}")

        if (userRepo.existsByUsername(request.username)) {
            LogUtil.info("${request.username} already exists")
            throw AlreadyExistsException("User already exists")
        }

        val role = roleRepo.findByRoleName(request.role)
            ?: run {
                LogUtil.info("${request.role} doesn't exists")
                throw NotFoundException("Role doesn't exists")
            }

        val user = User(
            username = request.username,
            password = passwordEncoder.encode(request.password),
            role = role
        )

        userRepo.save(user)
        LogUtil.info("${user.username} created successfully")
    }

    override fun getAll(): List<UserResponse> {
        LogUtil.info("Fetching all users")

        val users = userRepo.findAll().toResponse()
        if (users.isEmpty()) {
            LogUtil.info("No users found")
            return emptyList()
        }

        LogUtil.info("Fetched ${users.size} users")
        return users
    }

    override fun deleteUser(id: UUID) {
        LogUtil.info("deleting user with id $id")
        if (!userRepo.existsById(id)) {
            LogUtil.error("Cannot delete, User with id $id does not exist")
            throw AlreadyExistsException("user with id $id does not exist")
        }

        userRepo.deleteById(id)
        LogUtil.info("User deleted successfully with id: $id")
    }

    override fun assignRole(userId: UUID, request: AssignRoleRequest) {
        val user = userRepo.findById(userId).orElseThrow {
            LogUtil.error("user not found with id: $userId")
            NotFoundException("user not found with id: $userId")
        }

        if (user.role?.roleName == request.roleName) {
            LogUtil.error("User already has the role: ${request.roleName}")
            throw AlreadyExistsException("User already has the role: ${request.roleName}")
        }

        val role = roleRepo.findByRoleName(request.roleName)
            ?: run {
                LogUtil.error("role not found with name: ${request.roleName}")
                throw NotFoundException("role not found with name: ${request.roleName}")
            }

        user.role = role
        userRepo.save(user)
        LogUtil.info("User assigned role ${role.roleName} successfully")
    }
}