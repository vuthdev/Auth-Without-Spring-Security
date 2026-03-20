package firestorm.vuth.springbootauth.service.impl

import firestorm.vuth.springbootauth.dto.request.AuthRequest
import firestorm.vuth.springbootauth.dto.request.CreateUserRequest
import firestorm.vuth.springbootauth.dto.response.LoginResponse
import firestorm.vuth.springbootauth.dto.response.RegisterResponse
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
import firestorm.vuth.springbootauth.dto.response.ProfileResponse
import firestorm.vuth.springbootauth.mapper.toProfileResponse
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

    override fun register(request: AuthRequest): RegisterResponse {
        if (userRepo.existsByUsername(request.username)) {
            throw UnauthorizedException("User already exists")
        }

        val defaultRole = roleRepo.findByRoleName("user") ?: throw NotFoundException("role not found")

        val user = User(
            username = request.username,
            password = passwordEncoder.encode(request.password),
            role = defaultRole
        )

        userRepo.save(user)
        return RegisterResponse(
            success = true,
            message = "User registered successfully",
        )
    }

    override fun viewProfile(): ProfileResponse {
        val user = authContext.getCurrentUser()
        return user.toProfileResponse()
    }

    override fun createUser(request: CreateUserRequest): UserResponse {
        if (userRepo.existsByUsername(request.username)) {
            throw UnauthorizedException("User already exists")
        }

        val role = roleRepo.findByRoleName(request.role) ?: throw NotFoundException("role not found")

        val user = User(
            username = request.username,
            password = passwordEncoder.encode(request.password),
            role = role
        )

        return userRepo.save(user).toResponse()
    }

    override fun getAll(): List<UserResponse> {
        return userRepo.findAll().toResponse()
    }

    override fun deleteUser(id: UUID) {
        userRepo.deleteById(id)
    }
}