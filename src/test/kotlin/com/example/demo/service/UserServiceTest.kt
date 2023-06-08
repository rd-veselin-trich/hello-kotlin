package com.example.demo.service

import com.example.demo.entity.Person
import com.example.demo.entity.User
import com.example.demo.model.users.NewUser
import com.example.demo.model.users.PersonDto
import com.example.demo.model.users.UserResponse
import com.example.demo.repo.UserRepository
import com.example.demo.service.mappers.PersonMapper
import com.example.demo.service.mappers.UserMapper
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

class UserServiceTest {

    private lateinit var userRepository: UserRepository
    private lateinit var personService: PersonService
    private lateinit var passwordEncoder: PasswordEncoder
    private lateinit var userMapper: UserMapper
    private lateinit var personMapper: PersonMapper
    private lateinit var userService: UserService

    @BeforeEach
    fun setup() {
        userRepository = mockk()
        personService = mockk()
        passwordEncoder = mockk()
        userMapper = mockk()
        personMapper = mockk()
        userService = UserService(userRepository, personService, passwordEncoder, userMapper, personMapper)
    }

    @Test
    fun getUsers_ShouldReturnListOfUserResponses() {
        val uuid: UUID = UUID.randomUUID()

        val users =
            listOf(User(1, "test@example.com", "encodedPassword", Person("1", uuid, "John", "Doe", "123456789")))
        val userResponse = UserResponse(1, "test@example.com", PersonDto(uuid, "John", "Doe", "123456789"))
        val userResponses = listOf(userResponse)

        every { userRepository.findAll() } returns users
        every { userMapper.entityToResponse(any()) } returns userResponse

        val result = userService.getUsers()

        assertEquals(userResponses, result)
    }

    @Test
    fun createUser_ShouldReturnCreatedUserResponse() {
        val uuid: UUID = UUID.randomUUID()

        val newUser = NewUser("test@example.com", "password", "1", "123456789")
        val person = Person("1", uuid, "John", "Doe", "123456789")
        val personDto = PersonDto(uuid, "John", "Doe", "123456789")
        val userEntity = User(1, "test@example.com", "encodedPassword", person)
        val createdUserResponse = UserResponse(1, "test@example.com", PersonDto(uuid, "John", "Doe", "123456789"))

        every { personService.findById(newUser.personId) } returns personDto
        every { personMapper.dtoToEntity(personDto) } returns person
        every { passwordEncoder.encode(newUser.password) } returns "encodedPassword"
        every { userRepository.save(any()) } returns userEntity
        every { userMapper.entityToResponse(userEntity) } returns createdUserResponse

        val result = userService.createUser(newUser)

        assertEquals(createdUserResponse, result)
    }
}