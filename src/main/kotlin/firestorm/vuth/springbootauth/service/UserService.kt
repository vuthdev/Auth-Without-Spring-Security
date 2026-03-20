package firestorm.vuth.springbootauth.service

import firestorm.vuth.springbootauth.dto.req.AuthRequest
import firestorm.vuth.springbootauth.dto.req.CreateUserRequest
import firestorm.vuth.springbootauth.dto.res.LoginResponse
import firestorm.vuth.springbootauth.dto.res.ProfileResponse
import firestorm.vuth.springbootauth.dto.res.RegisterResponse
import firestorm.vuth.springbootauth.dto.res.UserResponse
import firestorm.vuth.springbootauth.model.User
import java.util.UUID

interface UserService {
    fun login(request: AuthRequest): LoginResponse
    fun register(request: AuthRequest): RegisterResponse
    fun viewProfile(): ProfileResponse
    fun createUser(request: CreateUserRequest): UserResponse
    fun getAll(): List<UserResponse>
    fun deleteUser(id: UUID)
}