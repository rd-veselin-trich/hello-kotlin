package com.example.demo.service

import com.example.demo.entity.User
import com.example.demo.model.NewUser
import com.example.demo.model.UserResponse
import com.example.demo.repo.UserRepository
import jakarta.annotation.PostConstruct
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {

    @PostConstruct
    fun init() {
        val user = userRepository.findByEmail("veso@veso.com")
            .orElseGet { User(null, "Veso", "veso@veso.com", passwordEncoder.encode("123456")) }
        if (user.id == null){
            userRepository.save(user)
        }
    }

    fun getUsers(): List<UserResponse> {
        return userRepository.findAll().map { user -> entityToResponse(user) }.toList()
    }

    fun createUser(user: NewUser): UserResponse {
        val userEntity = User(null, user.name, user.email, passwordEncoder.encode(user.password))
        return entityToResponse(userRepository.save(userEntity))
    }

    fun entityToResponse(user: User): UserResponse {
        return UserResponse(user.id, user.name, user.email)
    }
}