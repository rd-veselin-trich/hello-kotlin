package com.example.demo.controller

import com.example.demo.service.UserService
import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest
class UserControllerTest(private val mockMvc: MockMvc) {

    @MockkBean
    lateinit var userService: UserService

    @Test
    fun getAllUsers() {

    }

    @Test
    fun createUser() {
    }

    @Test
    fun getUserService() {
    }

}