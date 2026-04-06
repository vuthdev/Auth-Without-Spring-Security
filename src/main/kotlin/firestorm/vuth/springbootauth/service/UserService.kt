package firestorm.vuth.springbootauth.service

import firestorm.vuth.springbootauth.dto.request.AssignRoleRequest
import firestorm.vuth.springbootauth.dto.request.AuthRequest
import firestorm.vuth.springbootauth.dto.request.CreateUserRequest
import firestorm.vuth.springbootauth.dto.request.RefreshTokenRequest
import firestorm.vuth.springbootauth.dto.response.AuthResponse
import firestorm.vuth.springbootauth.dto.response.ProfileResponse
import firestorm.vuth.springbootauth.dto.response.UserResponse
import java.util.UUID

interface UserService {
    fun login(request: AuthRequest): AuthResponse
    fun register(request: AuthRequest)
    fun refresh(request: RefreshTokenRequest): AuthResponse
    fun viewProfile(): ProfileResponse
    fun viewUserProfile(userId: UUID): ProfileResponse
    fun createUser(request: CreateUserRequest)
    fun getAll(): List<UserResponse>
    fun deleteUser(id: UUID)
    fun assignRole(userId: UUID, request: AssignRoleRequest)
}