package com.example.demo.service

import com.example.demo.entity.User
import com.example.demo.model.users.NewUser
import com.example.demo.model.users.UserResponse
import com.example.demo.repo.UserRepository
import com.example.demo.service.mappers.PersonMapper
import com.example.demo.service.mappers.UserMapper
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val personService: PersonService,
    private val passwordEncoder: PasswordEncoder,
    private val userMapper: UserMapper,
    private val personMapper: PersonMapper
) {

    fun getUsers(): List<UserResponse> {
        return userRepository.findAll().map { user -> userMapper.entityToResponse(user) }.toList()
    }

    fun createUser(user: NewUser): UserResponse {
        val userEntity = User(
            null,
            user.email,
            passwordEncoder.encode(user.password),
            personMapper.dtoToEntity(personService.findById(user.personId))
        )
        return userMapper.entityToResponse(userRepository.save(userEntity))
    }

}