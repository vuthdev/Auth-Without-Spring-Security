package firestorm.vuth.springbootauth.controller

import firestorm.vuth.springbootauth.dto.req.CreateRoleRequest
import firestorm.vuth.springbootauth.dto.req.UpdateRoleRequest
import firestorm.vuth.springbootauth.dto.res.RoleResponse
import firestorm.vuth.springbootauth.service.RoleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/roles")
class RoleController(
    private val roleService: RoleService,
) {
    @PostMapping
    fun createRole(@RequestBody request: CreateRoleRequest): ResponseEntity<RoleResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(request))
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