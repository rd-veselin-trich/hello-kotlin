package com.example.demo.controller

import com.example.demo.model.NewUser
import com.example.demo.model.UserResponse
import com.example.demo.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @GetMapping
    @Operation(summary = "Sets a price for a chosen car", description = "Returns 202 if successful")
    @ApiResponse(responseCode = "200", description = "Successful")
    fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        return ResponseEntity.ok(userService.getUsers())
    }

    @PostMapping
    @ApiResponses(value = [
        ApiResponse(description = "Created", responseCode = "201"),
        ApiResponse(description = "Bad Request", responseCode = "400")
    ])
    fun createUser(@RequestBody() user: NewUser): ResponseEntity<UserResponse> {
        return ResponseEntity(userService.createUser(user),HttpStatus.CREATED)
    }


}