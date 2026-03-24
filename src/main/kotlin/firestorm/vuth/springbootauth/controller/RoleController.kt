package firestorm.vuth.springbootauth.controller

import firestorm.vuth.springbootauth.annotation.RequiresPermission
import firestorm.vuth.springbootauth.dto.request.AddPermissionRequest
import firestorm.vuth.springbootauth.dto.request.CreateRoleRequest
import firestorm.vuth.springbootauth.dto.request.RemovePermissionRequest
import firestorm.vuth.springbootauth.dto.request.UpdateRoleRequest
import firestorm.vuth.springbootauth.dto.response.RoleResponse
import firestorm.vuth.springbootauth.service.RoleService
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
@RequestMapping("/roles")
class RoleController(
    private val roleService: RoleService,
) {
    @PostMapping
    @RequiresPermission("CREATE_ROLE")
    fun createRole(@RequestBody request: CreateRoleRequest): ResponseEntity<RoleResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(request))
    }

    @DeleteMapping("/{id}")
    @RequiresPermission("DELETE_ROLE")
    fun deleteRole(@PathVariable id: UUID): ResponseEntity<Unit> {
        return ResponseEntity.ok(roleService.deleteById(id))
    }

    @GetMapping
    @RequiresPermission("READ_ROLE")
    fun getAll(): ResponseEntity<List<RoleResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.findAll())
    }

    @GetMapping("/{roleName}/permissions")
    @RequiresPermission("READ_ROLE_PERMISSION")
    fun getRolePermission(@PathVariable roleName: String): ResponseEntity<RoleResponse> {
        return ResponseEntity.ok(roleService.getAllRolePermissions(roleName))
    }

    @PostMapping("/{roleName}/permissions")
    @RequiresPermission("ADD_PERMISSION")
    fun addPermissionToRole(@PathVariable roleName: String, @RequestBody request: AddPermissionRequest): ResponseEntity<RoleResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.addPermissionToRole(roleName, request))
    }

    @PutMapping
    @RequiresPermission("UPDATE_ROLE")
    fun updateRole(@RequestBody request: UpdateRoleRequest): ResponseEntity<RoleResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.updateRole(request))
    }

    @DeleteMapping("/{roleName}/permissions")
    @RequiresPermission("DELETE_PERMISSION")
    fun removePermFromRole(
        @PathVariable roleName: String,
        @RequestBody request: RemovePermissionRequest
    ): ResponseEntity<RoleResponse> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(roleService.removePermissionFromRole(roleName, request))
    }
}