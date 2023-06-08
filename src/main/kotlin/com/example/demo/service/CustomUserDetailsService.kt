package com.example.demo.service

import com.example.demo.model.users.SecurityUser
import com.example.demo.repo.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username).orElseThrow { RuntimeException() }
        return SecurityUser(user.password, user.email)
    }

}