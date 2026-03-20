package firestorm.vuth.springbootauth.controller

import firestorm.vuth.springbootauth.dto.req.CreatePermissionRequest
import firestorm.vuth.springbootauth.dto.res.PermissionResponse
import firestorm.vuth.springbootauth.service.PermissionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/permissions")
class PermissionController(
    private val permissionService: PermissionService
) {
    @PostMapping
    fun createPermission(@RequestBody request: CreatePermissionRequest): ResponseEntity<PermissionResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionService.createPermissions(request))
    }

    @GetMapping
    fun listAllPermissions(): ResponseEntity<List<PermissionResponse>> {
        return ResponseEntity.ok(permissionService.findAll())
    }
}