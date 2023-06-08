package com.example.demo.controller

import com.example.demo.model.users.PersonDto
import com.example.demo.service.PersonService
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*

class PersonControllerTest {

    private lateinit var personService: PersonService
    private lateinit var personController: PersonController
    private lateinit var mockMvc: MockMvc
    private val objectMapper = ObjectMapper()

    @BeforeEach
    fun setup() {
        personService = mockk()
        personController = PersonController(personService)
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build()
    }

    @Test
    fun createPerson_ShouldReturnCreatedPersonDto() {
        val personDto = PersonDto(
            uuid = UUID.randomUUID(),
            firstName = "John",
            lastName = "Doe",
            address = "123 Main St"
        )
        val registeredPersonDto = PersonDto(
            uuid = personDto.uuid,
            firstName = "John",
            lastName = "Doe",
            address = "123 Main St"
        )

        every { personService.registerPerson(personDto) } returns registeredPersonDto

        val requestBuilder: RequestBuilder = MockMvcRequestBuilders
            .post("/api/v1/persons")
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(personDto))
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(requestBuilder)
            .andExpect(status().isCreated)
            .andExpect(content().json(objectMapper.writeValueAsString(registeredPersonDto)))

        verify { personService.registerPerson(personDto) }
    }
}