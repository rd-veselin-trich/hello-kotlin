package com.example.demo.service

import com.example.demo.entity.SpeedCamera
import com.example.demo.model.speedCamera.SpeedCameraDto
import com.example.demo.repo.SpeedCameraRepository
import com.example.demo.service.exceptions.SpeedCameraNotFoundException
import com.example.demo.service.mappers.SpeedCameraMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class SpeedCameraServiceTest {

    private lateinit var speedCameraService: SpeedCameraService

    private lateinit var speedCameraRepository: SpeedCameraRepository

    private lateinit var speedCameraMapper: SpeedCameraMapper

    @BeforeEach
    fun setUp() {
        speedCameraRepository = mockk()
        speedCameraMapper = mockk()
        speedCameraService = SpeedCameraService(speedCameraRepository, speedCameraMapper)
    }

    @Test
    fun registerSpeedCamera() {
        val speedCamera = SpeedCamera(
            id = 1L,
            latitude = 1.0f,
            longitude = 1.0f,
            inCity = false,
            manufacturer = "Manufacturer",
            speedLimit = 50
        )
        val speedCameraDto = SpeedCameraDto(
            id = 1L,
            latitude = 1.0f,
            longitude = 1.0f,
            inCity = false,
            manufacturer = "Manufacturer",
            speedLimit = 50
        )

        every { speedCameraRepository.save(any()) } returns speedCamera
        every { speedCameraMapper.entityToDto(any()) } returns speedCameraDto
        every { speedCameraMapper.dtoToEntity(any()) } returns speedCamera

        val result = speedCameraService.registerSpeedCamera(speedCameraDto)

        assertEquals(result, speedCameraDto)

        verify { speedCameraRepository.save(any()) }
        verify { speedCameraMapper.entityToDto(any()) }
        verify { speedCameraMapper.dtoToEntity(any()) }

    }

    @Test
    fun moveSpeedCamera_ReturnsDto() {
        val speedCamera = SpeedCamera(
            id = 1L,
            latitude = 1.0f,
            longitude = 1.0f,
            inCity = false,
            manufacturer = "Manufacturer",
            speedLimit = 50
        )
        val speedCameraDto = SpeedCameraDto(
            id = 1L,
            latitude = 1.0f,
            longitude = 1.0f,
            inCity = false,
            manufacturer = "Manufacturer",
            speedLimit = 50
        )

        every { speedCameraRepository.findById(any()) } returns Optional.of(speedCamera)
        every { speedCameraRepository.save(any()) } returns speedCamera
        every { speedCameraMapper.entityToDto(any()) } returns speedCameraDto
        every { speedCameraMapper.dtoToEntity(any()) } returns speedCamera

        val result = speedCameraService.moveSpeedCamera(1L, speedCameraDto)

        assertEquals(result, speedCameraDto)

        verify { speedCameraRepository.findById(any()) }
        verify { speedCameraRepository.save(any()) }
        verify { speedCameraMapper.entityToDto(any()) }

    }

    @Test
    fun moveSpeedCamera_ThrowsException() {
        val speedCameraDto = SpeedCameraDto(
            id = 1L,
            latitude = 1.0f,
            longitude = 1.0f,
            inCity = false,
            manufacturer = "Manufacturer",
            speedLimit = 50
        )

        every { speedCameraRepository.findById(any()) } returns Optional.empty()

        assertThrows<SpeedCameraNotFoundException> {
            speedCameraService.moveSpeedCamera(1L, speedCameraDto)
        }


        verify { speedCameraRepository.findById(any()) }
    }


    @Test
    fun retireSpeedCamera() {
        every { speedCameraRepository.deleteById(any()) } returns Unit

        speedCameraService.retireSpeedCamera(1L)

        verify { speedCameraRepository.deleteById(any()) }
    }

    @Test
    fun getCamera_ReturnsDto() {
        val speedCamera = SpeedCamera(
            id = 1L,
            latitude = 1.0f,
            longitude = 1.0f,
            inCity = false,
            manufacturer = "Manufacturer",
            speedLimit = 50
        )

        val speedCameraDto = SpeedCameraDto(
            id = 1L,
            latitude = 1.0f,
            longitude = 1.0f,
            inCity = false,
            manufacturer = "Manufacturer",
            speedLimit = 50
        )

        every { speedCameraRepository.findById(any()) } returns Optional.of(speedCamera)
        every { speedCameraMapper.entityToDto(any()) } returns speedCameraDto

        val result = speedCameraService.getCamera(1L)

        assertEquals(result, speedCameraDto)

        verify { speedCameraRepository.findById(any()) }
        verify { speedCameraMapper.entityToDto(any()) }
    }

    @Test
    fun getCamera_ThrowsException() {
        every { speedCameraRepository.findById(any()) } returns Optional.empty()

        assertThrows<SpeedCameraNotFoundException> {
            speedCameraService.getCamera(1L)
        }

        verify { speedCameraRepository.findById(any()) }
    }
}