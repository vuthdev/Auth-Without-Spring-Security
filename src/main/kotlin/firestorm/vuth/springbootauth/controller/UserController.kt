package firestorm.vuth.springbootauth.controller

import firestorm.vuth.springbootauth.annotation.RequiresPermission
import firestorm.vuth.springbootauth.dto.request.AssignRoleRequest
import firestorm.vuth.springbootauth.dto.request.AuthRequest
import firestorm.vuth.springbootauth.dto.request.CreateUserRequest
import firestorm.vuth.springbootauth.dto.response.BaseResponse
import firestorm.vuth.springbootauth.dto.response.LoginResponse
import firestorm.vuth.springbootauth.dto.response.ProfileResponse
import firestorm.vuth.springbootauth.dto.response.UserResponse
import firestorm.vuth.springbootauth.service.UserService
import firestorm.vuth.springbootauth.utils.ApiSuccess
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    @PostMapping
    @RequiresPermission("CREATE_USERS")
    fun createUser(@Valid @RequestBody request: CreateUserRequest): ResponseEntity<BaseResponse<Nothing>> {
        userService.createUser(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiSuccess.message(
                message = "Created user successfully"
            )
        )
    }

    @GetMapping
    @RequiresPermission("READ_USERS")
    fun findAll(): ResponseEntity<BaseResponse<List<UserResponse>>> {
        return ResponseEntity.ok(
            ApiSuccess.withData(
                data = userService.getAll(),
                message = "Users retrieved successfully"
            )
        )
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: AuthRequest): ResponseEntity<BaseResponse<LoginResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiSuccess.withData(
                data = userService.login(request),
                message = "Login successful"
            )
        )
    }

    @GetMapping("/profile")
    @RequiresPermission("VIEW_PROFILE")
    fun profile(): ResponseEntity<BaseResponse<ProfileResponse>> {
        return ResponseEntity.ok(
            ApiSuccess.withData(
                data = userService.viewProfile(),
                message = "Profile retrieved successfully"
            )
        )
    }

    @GetMapping("/profile/{userId}")
    @RequiresPermission("VIEW_OTHER_PROFILE")
    fun profileOther(@PathVariable userId: UUID): ResponseEntity<BaseResponse<ProfileResponse>> {
        return ResponseEntity.ok(
            ApiSuccess.withData(
                data = userService.viewUserProfile(userId),
                message = "Profile retrieved successfully"
            )
        )
    }

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: AuthRequest): ResponseEntity<BaseResponse<Nothing>> {
        userService.register(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiSuccess.message(
                status = HttpStatus.CREATED,
                message = "Registration successful"
            )
        )
    }

    @DeleteMapping("/{userId}")
    @RequiresPermission("DELETE_USERS")
    fun deleteUser(@PathVariable userId: UUID): ResponseEntity<BaseResponse<Nothing>> {
        userService.deleteUser(userId)
        return ResponseEntity.ok(
            ApiSuccess.message(
                message = "User deleted successfully"
            )
        )
    }

    @PutMapping("/{userId}/roles")
    @RequiresPermission("ASSIGN_ROLE")
    fun assignRole(
        @PathVariable userId: UUID,
        @Valid @RequestBody request: AssignRoleRequest
    ): ResponseEntity<BaseResponse<Nothing>> {
        userService.assignRole(userId, request)
        return ResponseEntity.ok(
            ApiSuccess.message(
                message = "Role assigned successfully"
            )
        )
    }
}