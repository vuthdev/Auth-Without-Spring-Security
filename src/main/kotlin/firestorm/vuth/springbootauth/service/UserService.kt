package firestorm.vuth.springbootauth.service

import firestorm.vuth.springbootauth.dto.request.AssignRoleRequest
import firestorm.vuth.springbootauth.dto.request.AuthRequest
import firestorm.vuth.springbootauth.dto.request.CreateUserRequest
import firestorm.vuth.springbootauth.dto.response.LoginResponse
import firestorm.vuth.springbootauth.dto.response.ProfileResponse
import firestorm.vuth.springbootauth.dto.response.RegisterResponse
import firestorm.vuth.springbootauth.dto.response.RoleResponse
import firestorm.vuth.springbootauth.dto.response.UserResponse
import java.util.UUID

interface UserService {
    fun login(request: AuthRequest): LoginResponse
    fun register(request: AuthRequest): RegisterResponse
    fun viewProfile(): ProfileResponse
    fun viewUserProfile(username: String): ProfileResponse
    fun createUser(request: CreateUserRequest): UserResponse
    fun getAll(): List<UserResponse>
    fun deleteUser(id: UUID)
    fun assignRole(username: String, request: AssignRoleRequest): UserResponse
}