package com.example.demo.controller

import com.example.demo.model.users.NewUser
import com.example.demo.model.users.PersonDto
import com.example.demo.model.users.UserResponse
import com.example.demo.service.UserService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*

class UserControllerTest {

    private lateinit var userService: UserService
    private lateinit var mockMvc: MockMvc
    private lateinit var userController: UserController

    @BeforeEach
    fun setup() {
        userService = mockk()
        userController = UserController(userService)
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build()
    }

    @Test
    fun getAllUsers_ShouldReturnListOfUsers() {
        val users =
            listOf(UserResponse(1, "test@example.com", PersonDto(UUID.randomUUID(), "John", "Doe", "123456789")))

        every { userService.getUsers() } returns users

        mockMvc.perform(get("/api/v1/users"))
            .andExpect(status().isOk)
    }

    @Test
    fun createUser_ShouldReturnCreatedUser() {
        val newUser = NewUser("John", "test@example.com", "password", "123456789")
        val createdUser = UserResponse(1, "test@example.com", PersonDto(UUID.randomUUID(), "John", "Doe", "123456789"))

        every { userService.createUser(newUser) } returns createdUser

        mockMvc.perform(
            post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"name": "John","email": "test@example.com", "password": "password", "personId": "123456789"}""")
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("""
                {
                    "id": 1,
                    "email": "test@example.com",
                    "person": {
                        "uuid": "${createdUser.person.uuid}",
                        "firstName": "John",
                        "lastName": "Doe",
                        "address": "123456789"
                    }
                }
            """.trimIndent()))
    }

}