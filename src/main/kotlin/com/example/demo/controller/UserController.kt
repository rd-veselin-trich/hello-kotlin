package com.example.demo.controller

import com.example.demo.model.users.NewUser
import com.example.demo.model.users.UserResponse
import com.example.demo.service.UserService
import com.example.demo.service.exceptions.PersonNotFoundException
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
    @Operation(summary = "Get all users")
    @ApiResponse(responseCode = "200", description = "Successful")
    fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        return ResponseEntity.ok(userService.getUsers())
    }

    @PostMapping
    @Operation(summary = "Create a user")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Created"),
            ApiResponse(responseCode = "400", description = "Bad Request")
        ]
    )
    fun createUser(@RequestBody() user: NewUser): ResponseEntity<UserResponse> {
        return try {
            ResponseEntity(userService.createUser(user), HttpStatus.CREATED)
        } catch (personNotFound: PersonNotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

}