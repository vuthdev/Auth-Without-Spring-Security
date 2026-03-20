package firestorm.vuth.springbootauth.controller

import firestorm.vuth.springbootauth.dto.request.CreatePermissionRequest
import firestorm.vuth.springbootauth.dto.response.PermissionResponse
import firestorm.vuth.springbootauth.service.PermissionService
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
    fun createPermission(@RequestBody request: CreatePermissionRequest): ResponseEntity<PermissionResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionService.createPermissions(request))
    }

    @DeleteMapping("/{id}")
    fun deletePermission(@PathVariable id: UUID): ResponseEntity<Unit> {
        return ResponseEntity.ok(permissionService.deleteById(id))
    }

    @GetMapping
    fun listAllPermissions(): ResponseEntity<List<PermissionResponse>> {
        return ResponseEntity.ok(permissionService.findAll())
    }
}