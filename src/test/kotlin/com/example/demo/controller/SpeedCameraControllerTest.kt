package com.example.demo.controller

import com.example.demo.model.speedCamera.SpeedCameraDto
import com.example.demo.service.SpeedCameraService
import com.example.demo.service.exceptions.SpeedCameraNotFoundException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class SpeedCameraControllerTest {

    private lateinit var speedCameraService: SpeedCameraService
    private lateinit var mockMvc: MockMvc
    private lateinit var speedCameraController: SpeedCameraController

    @BeforeEach
    fun setup() {
        speedCameraService = mockk()
        speedCameraController = SpeedCameraController(speedCameraService)
        mockMvc = MockMvcBuilders.standaloneSetup(speedCameraController).build()
    }

    @Test
    fun createSpeedCamera_ShouldReturnSpeedCameraDto() {
        val speedCameraDto = SpeedCameraDto(
            id = 1L,
            latitude = 1.0f,
            longitude = 1.0f,
            inCity = false,
            manufacturer = "Manufacturer",
            speedLimit = 50
        )

        every { speedCameraService.registerSpeedCamera(any()) } returns speedCameraDto

        mockMvc.perform(
            post("/api/v1/speed-cameras")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"latitude": 1.0, "longitude": 1.0, "inCity": false, "manufacturer": "Manufacturer", "speedLimit": 50}""")
        )
            .andExpect(status().isCreated)
            .andExpect(content().json("""{"id": 1, "latitude": 1.0, "longitude": 1.0, "inCity": false, "manufacturer": "Manufacturer", "speedLimit": 50}"""))

    }

    @Test
    fun moveSpeedCamera_AndReturnSpeedCameraDto() {
        val speedCameraDto = SpeedCameraDto(
            id = 1L,
            latitude = 1.0f,
            longitude = 1.0f,
            inCity = false,
            manufacturer = "Manufacturer",
            speedLimit = 50
        )

        every { speedCameraService.moveSpeedCamera(1L, speedCameraDto) } returns speedCameraDto

        mockMvc.perform(
            patch("/api/v1/speed-cameras/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"id": 1, "latitude": 1.0, "longitude": 1.0, "inCity": false, "manufacturer": "Manufacturer", "speedLimit": 50}""")
        )
            .andExpect(status().isOk)
            .andExpect(content().json("""{"id": 1, "latitude": 1.0, "longitude": 1.0, "inCity": false, "manufacturer": "Manufacturer", "speedLimit": 50}"""))
    }

    @Test
    fun moveSpeedCamera_ReturnNotFound() {
        every { speedCameraService.moveSpeedCamera(1L, any()) } throws SpeedCameraNotFoundException()

        mockMvc.perform(
            patch("/api/v1/speed-cameras/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"id": 1, "latitude": 1.0, "longitude": 1.0, "inCity": false, "manufacturer": "Manufacturer", "speedLimit": 50}""")
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun retireSpeedCamera() {
        every { speedCameraService.retireSpeedCamera(1L) } returns Unit

        mockMvc.perform(
            delete("/api/v1/speed-cameras/1")
        )
            .andExpect(status().isOk)
    }
}