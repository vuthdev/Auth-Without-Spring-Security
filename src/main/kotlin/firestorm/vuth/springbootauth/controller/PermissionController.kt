package firestorm.vuth.springbootauth.controller

import firestorm.vuth.springbootauth.annotation.RequiresPermission
import firestorm.vuth.springbootauth.dto.request.CreatePermissionRequest
import firestorm.vuth.springbootauth.dto.response.ApiResponse
import firestorm.vuth.springbootauth.dto.response.PermissionResponse
import firestorm.vuth.springbootauth.service.PermissionService
import firestorm.vuth.springbootauth.utils.ApiSuccess
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/permissions")
class PermissionController(
    private val permissionService: PermissionService
) {
    @PostMapping
    @RequiresPermission("CREATE_PERMISSION")
    fun createPermission(
        @Valid @RequestBody request: CreatePermissionRequest
    ): ResponseEntity<ApiResponse<Nothing>> {
        permissionService.createPermissions(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiSuccess.message(
                status = HttpStatus.CREATED,
                message = "Permission created successfully"
            )
        )
    }

    @DeleteMapping("/{permissionId}")
    @RequiresPermission("DELETE_PERMISSION")
    fun deletePermission(@PathVariable permissionId: UUID): ResponseEntity<ApiResponse<Nothing>> {
        permissionService.deleteById(permissionId)
        return ResponseEntity.ok(
            ApiSuccess.message(
                message = "Permission deleted successfully",
            )
        )
    }

    @GetMapping
    @RequiresPermission("READ_PERMISSION")
    fun listAllPermissions(): ResponseEntity<ApiResponse<List<PermissionResponse>>> {
        return ResponseEntity.ok(
            ApiSuccess.withData(
                data = permissionService.findAll(),
                message = "Permissions retrieved successfully"
            )
        )
    }
}