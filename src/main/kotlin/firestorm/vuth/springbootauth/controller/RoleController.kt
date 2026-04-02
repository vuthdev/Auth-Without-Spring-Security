package firestorm.vuth.springbootauth.controller

import firestorm.vuth.springbootauth.annotation.RequiresPermission
import firestorm.vuth.springbootauth.annotation.RequiresRole
import firestorm.vuth.springbootauth.dto.request.AddPermissionRequest
import firestorm.vuth.springbootauth.dto.request.CreateRoleRequest
import firestorm.vuth.springbootauth.dto.request.RemovePermissionRequest
import firestorm.vuth.springbootauth.dto.request.UpdateRoleRequest
import firestorm.vuth.springbootauth.dto.response.BaseResponse
import firestorm.vuth.springbootauth.dto.response.RoleResponse
import firestorm.vuth.springbootauth.service.RoleService
import firestorm.vuth.springbootauth.utils.ApiSuccess
import io.github.oshai.kotlinlogging.KotlinLogging
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

private val log = KotlinLogging.logger {}

@RestController
@RequestMapping("/roles")
class RoleController(
    private val roleService: RoleService,
) {
    @PostMapping
    @RequiresPermission("CREATE_ROLE")
    fun createRole(
        @Valid @RequestBody request: CreateRoleRequest
    ): ResponseEntity<BaseResponse<Nothing>> {
        log.info { "Creating role ${request.roleName}" }
        roleService.createRole(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiSuccess.message(
                status = HttpStatus.CREATED,
                message = "Role created successfully"
            )
        )
    }

    @DeleteMapping("/{roleId}")
    @RequiresPermission("DELETE_ROLE")
    fun deleteRole(
        @PathVariable roleId: UUID
    ): ResponseEntity<BaseResponse<Nothing>> {
        roleService.deleteById(roleId)
        return ResponseEntity.ok(
            ApiSuccess.message(
                message = "Role deleted successfully"
            )
        )
    }

    @GetMapping
    @RequiresPermission("READ_ROLE")
    fun getAll(): ResponseEntity<BaseResponse<List<RoleResponse>>> {
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiSuccess.withData(
                data = roleService.findAll(),
                message = "Role retrieved successfully"
            )
        )
    }

    @GetMapping("/{roleId}/permissions")
    @RequiresPermission("READ_ROLE_PERMISSION")
    fun getRolePermission(@PathVariable roleId: UUID): ResponseEntity<BaseResponse<RoleResponse>> {
        return ResponseEntity.ok(
            ApiSuccess.withData(
                data = roleService.getAllRolePermissions(roleId),
                message = "Role permissions successfully retrieved"
            )
        )
    }

    @PostMapping("/{roleId}/permissions")
    @RequiresPermission("ADD_PERMISSION")
    @RequiresRole("admin")
    fun addPermissionToRole(
        @PathVariable roleId: UUID,
        @Valid @RequestBody request: AddPermissionRequest
    ): ResponseEntity<BaseResponse<Nothing>> {
        roleService.addPermissionToRole(roleId, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiSuccess.message(
                status = HttpStatus.CREATED,
                message = "Added permission successfully"
            )
        )
    }

    @PutMapping
    @RequiresPermission("UPDATE_ROLE")
    fun updateRole(
        @Valid @RequestBody request: UpdateRoleRequest
    ): ResponseEntity<BaseResponse<Nothing>> {
        roleService.updateRole(request)
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiSuccess.message(
                message = "Role updated successfully"
            )
        )
    }

    @DeleteMapping("/{roleId}/permissions")
    @RequiresPermission("DELETE_PERMISSION")
    fun removePermFromRole(
        @PathVariable roleId: UUID,
        @Valid @RequestBody request: RemovePermissionRequest
    ): ResponseEntity<BaseResponse<Nothing>> {
        roleService.removePermissionFromRole(roleId, request)
        return ResponseEntity.ok(
            ApiSuccess.message(
                message = "Role removed successfully"
            )
        )
    }
}