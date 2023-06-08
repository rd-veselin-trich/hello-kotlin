package com.example.demo.service

import com.example.demo.entity.User
import com.example.demo.model.users.SecurityUser
import com.example.demo.repo.UserRepository
import com.example.demo.service.exceptions.UserNotFoundException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class CustomUserDetailsServiceTest {

    private lateinit var customUserDetailsService: CustomUserDetailsService
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        customUserDetailsService = CustomUserDetailsService(userRepository)
    }

    @Test
    fun loadUserByUsername_ReturnsUserData() {
        val user = User(id = 1, email = "test@test.com", password = "password")
        val securityUser = SecurityUser(passwordDb = user.password, email = user.email)

        every { userRepository.findByEmail(any()) } returns Optional.of(user)

        val result = customUserDetailsService.loadUserByUsername(user.email)

        assertEquals(securityUser, result)

        verify(exactly = 1) { userRepository.findByEmail(any()) }
    }

    @Test
    fun loadUserByUsername_ThrowsException() {
        val user = User(id = 1, email = "test@test.com", password = "password")

        every { userRepository.findByEmail(any()) } returns Optional.empty()

        assertThrows<UserNotFoundException> {
            customUserDetailsService.loadUserByUsername(user.email)
        }
    }
}