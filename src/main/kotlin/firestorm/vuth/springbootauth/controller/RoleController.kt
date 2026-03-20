package firestorm.vuth.springbootauth.controller

import firestorm.vuth.springbootauth.dto.request.CreateRoleRequest
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
    fun createRole(@RequestBody request: CreateRoleRequest): ResponseEntity<RoleResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(request))
    }

    @DeleteMapping("/{id}")
    fun deleteRole(@PathVariable id: UUID): ResponseEntity<Unit> {
        return ResponseEntity.ok(roleService.deleteById(id))
    }

    @GetMapping
    fun getAll(): ResponseEntity<List<RoleResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.findAll())
    }

    @PutMapping
    fun updateRole(@RequestBody request: UpdateRoleRequest): ResponseEntity<RoleResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.updateRole(request))
    }
}