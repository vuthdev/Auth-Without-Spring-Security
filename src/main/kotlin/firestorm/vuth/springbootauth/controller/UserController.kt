package firestorm.vuth.springbootauth.controller

import firestorm.vuth.springbootauth.annotation.RequiresPermission
import firestorm.vuth.springbootauth.annotation.RequiresRole
import firestorm.vuth.springbootauth.dto.request.AssignRoleRequest
import firestorm.vuth.springbootauth.dto.request.AuthRequest
import firestorm.vuth.springbootauth.dto.request.CreateUserRequest
import firestorm.vuth.springbootauth.dto.response.LoginResponse
import firestorm.vuth.springbootauth.dto.response.ProfileResponse
import firestorm.vuth.springbootauth.dto.response.RegisterResponse
import firestorm.vuth.springbootauth.dto.response.RoleResponse
import firestorm.vuth.springbootauth.dto.response.UserResponse
import firestorm.vuth.springbootauth.service.UserService
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
    fun createUser(@RequestBody request: CreateUserRequest): ResponseEntity<UserResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request))
    }

    @GetMapping
    @RequiresPermission("READ_USERS")
    fun findAll(): ResponseEntity<List<UserResponse>> {
        return ResponseEntity.ok(userService.getAll())
    }

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(request))
    }

    @GetMapping("/profile")
    @RequiresPermission("VIEW_PROFILE")
    fun profile(): ResponseEntity<ProfileResponse> {
        return ResponseEntity.ok(userService.viewProfile())
    }

    @GetMapping("/profile/{username}")
    @RequiresPermission("VIEW_OTHER_PROFILE")
    fun profileOther(@PathVariable username: String): ResponseEntity<ProfileResponse> {
        return ResponseEntity.ok(userService.viewUserProfile(username))
    }

    @PostMapping("/register")
    fun register(@RequestBody request: AuthRequest): ResponseEntity<RegisterResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request))
    }

    @DeleteMapping("/{id}")
    @RequiresPermission("DELETE_USERS")
    fun deleteUser(@PathVariable id: UUID): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userService.deleteUser(id))
    }

    @PutMapping("/{username}/roles")
    @RequiresPermission("ASSIGN_ROLE")
    fun assignRole(
        @PathVariable username: String,
        @RequestBody request: AssignRoleRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.assignRole(username, request))
    }
}