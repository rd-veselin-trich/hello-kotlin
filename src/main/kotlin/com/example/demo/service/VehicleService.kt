package com.example.demo.service

import com.example.demo.entity.Vehicle
import com.example.demo.model.vehicle.NewVehicle
import com.example.demo.model.vehicle.VehicleDto
import com.example.demo.repo.VehicleRepository
import com.example.demo.service.exceptions.VehicleNotFoundException
import com.example.demo.service.mappers.PersonMapper
import com.example.demo.service.mappers.VehicleMapper
import org.springframework.stereotype.Service

@Service
class VehicleService(
    private val vehicleRepository: VehicleRepository,
    private val personService: PersonService,
    private val personMapper: PersonMapper,
    private val vehicleMapper: VehicleMapper
) {

    fun registerVehicle(newVehicle: NewVehicle): VehicleDto {
        val vehicle = Vehicle(
            plate = newVehicle.plate,
            owner = personMapper.dtoToEntity(personService.findById(newVehicle.owner))
        )
        return vehicleMapper.entityToDto(vehicleRepository.save(vehicle))
    }

    fun deleteVehicle(id: Long) {
        vehicleRepository.deleteById(id)
    }

    fun getVehicleByPlate(vehiclePlate: String): VehicleDto {
        return vehicleMapper.entityToDto(
            vehicleRepository.findByPlate(vehiclePlate).orElseThrow { VehicleNotFoundException() })
    }
}