package com.example.demo.controller

import com.example.demo.model.users.PersonDto
import com.example.demo.model.vehicle.VehicleDto
import com.example.demo.service.VehicleService
import com.example.demo.service.exceptions.PersonNotFoundException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*

class VehicleControllerTest {

    private lateinit var vehicleController: VehicleController
    private lateinit var vehicleService: VehicleService
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        vehicleService = mockk()
        vehicleController = VehicleController(vehicleService)
        mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).build()
    }

    @Test
    fun createVehicle_ReturnsDto() {
        val vehicleDto = VehicleDto(
            id = 1L,
            plate = "ABC-123",
            owner = PersonDto(uuid = UUID.randomUUID(), firstName = "First", lastName = "Last", address = "Address"),
        )

        every { vehicleService.registerVehicle(any()) } returns vehicleDto

        mockMvc.perform(
            post("/api/v1/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"plate": "ABC-123", "owner": "123456789"}""")
        )
            .andExpect(status().isCreated)
            .andExpect(content().json("""{"id": 1, "plate": "ABC-123", "owner": {"uuid": "${vehicleDto.owner.uuid}", "firstName": "First", "lastName": "Last", "address": "Address"}}"""))
    }

    @Test
    fun createVehicle_ReturnsNotFound() {
        every { vehicleService.registerVehicle(any()) } throws PersonNotFoundException()

        mockMvc.perform(
            post("/api/v1/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"plate": "ABC-123", "owner": "123456789"}""")
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun deleteVehicle() {
    }
}