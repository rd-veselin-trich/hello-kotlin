package com.example.demo.service

import com.example.demo.entity.User
import com.example.demo.model.NewUser
import com.example.demo.model.UserResponse
import com.example.demo.repo.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.security.crypto.password.PasswordEncoder

import java.util.*

class UserServiceTest {

    private val userRepository: UserRepository = mockk()
    private val passwordEncoder: PasswordEncoder = mockk();
    private val userService: UserService = UserService(userRepository, passwordEncoder)

    @Test
    fun getUsers() {
        val userList = Collections.singletonList(User(1L, "Test User", "test@user.com", "securePass"))
        val userResponseList = Collections.singletonList(UserResponse(1L, "Test User", "test@user.com"))

        every { userRepository.findAll() } returns userList

        val result = userService.getUsers();

        verify(exactly = 1) { userRepository.findAll() }

        assertEquals(userResponseList, result)
    }

    @Test
    fun createUser() {
        val createdEntity = buildUser()
        val newUser = buildNewUser()
        val createdUser = buildUserResponse()
        val securePass = "securePass"

        every { passwordEncoder.encode(any()) } returns securePass
        every { userRepository.save(any()) } returns createdEntity

        val result = userService.createUser(newUser)

        verify(exactly = 1) { passwordEncoder.encode(any()) }
        verify(exactly = 1) { userRepository.save(any()) }

        assertEquals(result, createdUser)
    }

    private fun buildUser(): User {
        return User(1L, "Test User", "test@user.com", "securePass");
    }

    private fun buildUserResponse(): UserResponse {
        return UserResponse(1L, "Test User", "test@user.com");

    }

    private fun buildNewUser(): NewUser {
        return NewUser("Test User", "test@user.com", "securePass");
    }
}