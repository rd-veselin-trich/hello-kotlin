package com.example.demo.service.mappers

import com.example.demo.entity.Vehicle
import com.example.demo.model.vehicle.VehicleDto
import org.mapstruct.Mapper
import org.springframework.stereotype.Component

@Mapper
@Component
interface VehicleMapper {

    fun entityToDto(vehicle: Vehicle): VehicleDto

    fun dtoToEntity(vehicleDto: VehicleDto): Vehicle
}