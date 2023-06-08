package com.example.demo.service

import com.example.demo.model.vehicle.VehicleDto
import com.example.demo.repo.VehicleRepository
import com.example.demo.service.exceptions.VehicleNotFoundException
import com.example.demo.service.mappers.VehicleMapper
import org.springframework.stereotype.Service

@Service
class VehicleService(
    private val vehicleRepository: VehicleRepository,
    private val vehicleMapper: VehicleMapper
) {

    fun registerVehicle(vehicle: VehicleDto): VehicleDto {
        return vehicleMapper.entityToDto(vehicleRepository.save(vehicleMapper.dtoToEntity(vehicle)))
    }

    fun deleteVehicle(id: Long) {
        vehicleRepository.deleteById(id)
    }

    fun getVehicleByPlate(vehiclePlate: String): VehicleDto {
        return vehicleMapper.entityToDto(
            vehicleRepository.findByPlate(vehiclePlate).orElseThrow { VehicleNotFoundException() })
    }
}