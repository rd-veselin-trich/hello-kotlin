package com.example.demo.service

import com.example.demo.entity.Person
import com.example.demo.model.users.PersonDto
import com.example.demo.repo.PersonRepository
import com.example.demo.service.exceptions.PersonNotFoundException
import com.example.demo.service.mappers.PersonMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class PersonServiceTest {

    private lateinit var personRepository: PersonRepository
    private lateinit var personMapper: PersonMapper
    private lateinit var personService: PersonService

    @BeforeEach
    fun setup() {
        personRepository = mockk()
        personMapper = mockk()
        personService = PersonService(personRepository, personMapper)
    }

    @Test
    fun registerPerson_ShouldReturnRegisteredPersonDto() {
        val uuid = UUID.randomUUID()
        val personDto = PersonDto(
            uuid = uuid,
            firstName = "John",
            lastName = "Doe",
            address = "123 Main St"
        )
        val personEntity = Person(
            uuid = uuid,
            firstName = "John",
            lastName = "Doe",
            address = "123 Main St",
            id = "123456789"
        )
        val registeredPersonEntity = Person(
            uuid = uuid,
            firstName = "John",
            lastName = "Doe",
            address = "123 Main St",
            id = "123456789"
        )
        val registeredPersonDto = PersonDto(
            uuid = uuid,
            firstName = "John",
            lastName = "Doe",
            address = "123 Main St"
        )

        every { personMapper.dtoToEntity(personDto) } returns personEntity
        every { personMapper.entityToDto(registeredPersonEntity) } returns registeredPersonDto
        every { personRepository.save(personEntity) } returns registeredPersonEntity

        val result = personService.registerPerson(personDto)

        assertEquals(registeredPersonDto, result)

        verify { personMapper.dtoToEntity(personDto) }
        verify { personMapper.entityToDto(registeredPersonEntity) }
        verify { personRepository.save(personEntity) }
    }

    @Test
    fun findById_ShouldReturnPersonDto() {
        val uuid = UUID.randomUUID()
        val personId = "123456789"
        val personEntity = Person(
            uuid = uuid,
            firstName = "John",
            lastName = "Doe",
            address = "123 Main St",
            id = "123456789"
        )
        val personDto = PersonDto(
            uuid = uuid,
            firstName = "John",
            lastName = "Doe",
            address = "123 Main St"
        )

        every { personRepository.findById(personId) } returns Optional.of(personEntity)
        every { personMapper.entityToDto(personEntity) } returns personDto

        val result = personService.findById(personId)

        assertEquals(personDto, result)

        verify { personRepository.findById(personId) }
        verify { personMapper.entityToDto(personEntity) }
    }

    @Test
    fun findById_ShouldThrowPersonNotFoundException() {

        every { personRepository.findById(any()) } returns Optional.empty()

        assertThrows(PersonNotFoundException::class.java) {
            personService.findById("123456789")
        }
    }
}