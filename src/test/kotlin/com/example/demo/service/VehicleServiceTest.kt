package com.example.demo.service

import com.example.demo.entity.Person
import com.example.demo.entity.Vehicle
import com.example.demo.model.users.PersonDto
import com.example.demo.model.vehicle.NewVehicle
import com.example.demo.model.vehicle.VehicleDto
import com.example.demo.repo.VehicleRepository
import com.example.demo.service.exceptions.PersonNotFoundException
import com.example.demo.service.exceptions.VehicleNotFoundException
import com.example.demo.service.mappers.PersonMapper
import com.example.demo.service.mappers.VehicleMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class VehicleServiceTest {

    private lateinit var vehicleService: VehicleService
    private lateinit var vehicleRepository: VehicleRepository
    private lateinit var vehicleMapper: VehicleMapper
    private lateinit var personService: PersonService
    private lateinit var personMapper: PersonMapper

    @BeforeEach
    fun setUp() {
        vehicleRepository = mockk()
        vehicleMapper = mockk()
        personService = mockk()
        personMapper = mockk()
        vehicleService = VehicleService(vehicleRepository, vehicleMapper, personService, personMapper)
    }

    @Test
    fun registerVehicle_ReturnDto() {
        val newVehicle = NewVehicle(
            plate = "ABC123",
            owner = "123456789"
        )
        val vehicle = Vehicle(
            plate = "ABC123",
            owner = Person(
                id = "123456789",
                firstName = "Name",
                lastName = "LastName",
                address = "Address",
                uuid = UUID.randomUUID()
            )
        )
        val vehicleDto = VehicleDto(
            id = 1L,
            plate = "ABC123",
            owner = PersonDto(
                uuid = UUID.randomUUID(),
                firstName = "Name",
                lastName = "LastName",
                address = "Address"
            )
        )

        every { vehicleRepository.save(any()) } returns vehicle
        every { vehicleMapper.entityToDto(any()) } returns vehicleDto
        every { personService.findById(any()) } returns vehicleDto.owner
        every { personMapper.dtoToEntity(any()) } returns vehicle.owner

        val result = vehicleService.registerVehicle(newVehicle)

        assertEquals(vehicleDto, result)

        verify(exactly = 1) { vehicleRepository.save(any()) }
        verify(exactly = 1) { vehicleMapper.entityToDto(any()) }
        verify(exactly = 1) { personService.findById(any()) }
        verify(exactly = 1) { personMapper.dtoToEntity(any()) }

    }

    @Test
    fun registerVehicle_ThrowException() {
        every { personService.findById(any()) } throws PersonNotFoundException()

        assertThrows<PersonNotFoundException> {
            vehicleService.registerVehicle(
                NewVehicle(
                    plate = "ABC123",
                    owner = "123456789"
                )
            )
        }
        verify { personService.findById(any()) }
    }

    @Test
    fun deleteVehicle() {
        every { vehicleRepository.deleteById(any()) } returns Unit

        vehicleService.deleteVehicle(1L)

        verify { vehicleRepository.deleteById(any()) }

    }

    @Test
    fun getVehicleByPlate_ReturnDto() {
        val vehicle = Vehicle(
            plate = "ABC123",
            owner = Person(
                id = "123456789",
                firstName = "Name",
                lastName = "LastName",
                address = "Address",
                uuid = UUID.randomUUID()
            )
        )

        val vehicleDto = VehicleDto(
            id = 1L,
            plate = "ABC123",
            owner = PersonDto(
                uuid = UUID.randomUUID(),
                firstName = "Name",
                lastName = "LastName",
                address = "Address"
            )
        )

        every { vehicleRepository.findByPlate(any()) } returns Optional.of(vehicle)
        every { vehicleMapper.entityToDto(any()) } returns vehicleDto

        val result = vehicleService.getVehicleByPlate("ABC123")

        assertEquals(vehicleDto, result)

        verify { vehicleRepository.findByPlate(any()) }
        verify { vehicleMapper.entityToDto(any()) }
    }

    @Test
    fun getVehicleByPlate_ThrowException() {
        every { vehicleRepository.findByPlate(any()) } returns Optional.empty()

        assertThrows<VehicleNotFoundException> {
            vehicleService.getVehicleByPlate("ABC123")
        }

        verify { vehicleRepository.findByPlate(any()) }
    }
}