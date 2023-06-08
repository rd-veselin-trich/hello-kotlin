package com.example.demo.service

import com.example.demo.entity.Person
import com.example.demo.entity.SpeedCamera
import com.example.demo.entity.Vehicle
import com.example.demo.model.fine.FineDto
import com.example.demo.model.fine.FineIssueData
import com.example.demo.model.`speed-camera`.SpeedCameraDto
import com.example.demo.model.users.PersonDto
import com.example.demo.model.vehicle.VehicleDto
import com.example.demo.repo.FineRepository
import com.example.demo.service.mappers.FineMapper
import com.example.demo.service.mappers.SpeedCameraMapper
import com.example.demo.service.mappers.VehicleMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class FineServiceTest {

    private lateinit var fineRepository: FineRepository
    private lateinit var vehicleService: VehicleService
    private lateinit var speedCameraService: SpeedCameraService
    private lateinit var fineMapper: FineMapper
    private lateinit var speedCameraMapper: SpeedCameraMapper
    private lateinit var vehicleMapper: VehicleMapper
    private lateinit var fineService: FineService

    @BeforeEach
    fun setup() {
        fineRepository = mockk()
        vehicleService = mockk()
        speedCameraService = mockk()
        fineMapper = mockk()
        speedCameraMapper = mockk()
        vehicleMapper = mockk()
        fineService = FineService(
            fineRepository,
            vehicleService,
            speedCameraService,
            fineMapper,
            speedCameraMapper,
            vehicleMapper
        )
    }

    @Test
    fun issueFine_ShouldReturnFineDto() {
        val fineData = FineIssueData(cameraId = 1, detectedVehiclePlate = "ABC123", detectedSpeed = 80.0f)
        val speedCamera = SpeedCamera(
            id = 1,
            latitude = 42.0f,
            longitude = 32.0f,
            manufacturer = "Manufacturer",
            speedLimit = 50,
            inCity = true
        )
        val vehicle = Vehicle(
            id = 1,
            plate = "ABC123",
            owner = Person(
                id = "123456789",
                uuid = UUID.randomUUID(),
                firstName = "John",
                lastName = "Doe",
                address = "123 Main St"
            )
        )
        val speedCameraDto = SpeedCameraDto(
            id = 1,
            latitude = 42.0f,
            longitude = 32.0f,
            manufacturer = "Manufacturer",
            speedLimit = 50,
            inCity = true
        )
        val vehicleDto = VehicleDto(
            id = 1,
            plate = "ABC123",
            owner = PersonDto(
                uuid = UUID.randomUUID(),
                firstName = "John",
                lastName = "Doe",
                address = "123 Main St"
            )
        )
        val fineDto = FineDto(
            id = 1,
            issuer = speedCameraDto,
            detectedVehicle = vehicleDto,
            detectedSpeed = 80.0f,
            amountToPay = 50.0f
        )

        every { speedCameraService.getCamera(fineData.cameraId) } returns speedCameraDto
        every { vehicleService.getVehicleByPlate(fineData.detectedVehiclePlate) } returns vehicleDto
        every { fineMapper.entityToDto(fineRepository.save(any())) } returns fineDto
        every { speedCameraMapper.entityToDto(speedCamera) } returns speedCameraDto
        every { speedCameraMapper.dtoToEntity(speedCameraDto) } returns speedCamera
        every { vehicleMapper.entityToDto(vehicle) } returns vehicleDto
        every { vehicleMapper.dtoToEntity(vehicleDto) } returns vehicle

        val result = fineService.issueFine(fineData)

        assertEquals(fineDto, result)

        verify { speedCameraService.getCamera(fineData.cameraId) }
        verify { vehicleService.getVehicleByPlate(fineData.detectedVehiclePlate) }
        verify { fineMapper.entityToDto(fineRepository.save(any())) }
    }
}