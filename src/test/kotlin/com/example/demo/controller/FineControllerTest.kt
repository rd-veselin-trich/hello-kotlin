package com.example.demo.controller

import com.example.demo.model.fine.FineDto
import com.example.demo.model.fine.FineIssueData
import com.example.demo.model.`speed-camera`.SpeedCameraDto
import com.example.demo.model.users.PersonDto
import com.example.demo.model.vehicle.VehicleDto
import com.example.demo.service.FineService
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

class FineControllerTest {

    private lateinit var fineService: FineService
    private lateinit var mockMvc: MockMvc
    private lateinit var fineController: FineController

    @BeforeEach
    fun setup() {
        fineService = mockk()
        fineController = FineController(fineService)
        mockMvc = MockMvcBuilders.standaloneSetup(fineController).build()
    }

    @Test
    fun createFine_ShouldReturnFineDto() {
        val fineData = FineIssueData(78.0f, 1, "plate id")

        val fineDto = FineDto(
            1, SpeedCameraDto(1, 42.0f, 32.0f, "Manufacturer", 50, true), VehicleDto(
                1, "plate id", PersonDto(
                    UUID.randomUUID(), "John", "Doe", "123456789"
                )
            ), 100.0f, 1000.0f
        )

        every { fineService.issueFine(fineData) } returns fineDto

        mockMvc.perform(
            post("/api/v1/fines")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"detectedSpeed": 78.0, "cameraId": 1, "detectedVehiclePlate": "plate id"}""")
        )
            .andExpect(status().isCreated)
            .andExpect(
                content().json(
                    """
                    {
                        "id": 1,
                        "issuer": {
                            "id": 1,
                            "latitude": 42.0,
                            "longitude": 32.0,
                            "manufacturer": "Manufacturer",
                            "speedLimit": 50,
                            "inCity": true
                        },
                        "detectedVehicle": {
                            "id": 1,
                            "plate": "plate id",
                            "owner": {
                                "uuid": "${fineDto.detectedVehicle.owner.uuid}",
                                "firstName": "John",
                                "lastName": "Doe",
                                "address": "123456789"
                            }
                        },
                        "detectedSpeed": 100.0,
                        "amountToPay": 1000.0
                    }""".trimMargin()
                )
            )
    }
}