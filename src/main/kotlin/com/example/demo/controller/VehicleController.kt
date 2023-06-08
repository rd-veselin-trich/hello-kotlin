package com.example.demo.controller

import com.example.demo.model.vehicle.VehicleDto
import com.example.demo.service.VehicleService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/vehicles")
class VehicleController(private val vehicleService: VehicleService) {


    @PostMapping
    fun createVehicle(@RequestBody vehicle: VehicleDto): VehicleDto {
        return vehicleService.registerVehicle(vehicle)
    }

    @DeleteMapping("/{id}")
    fun deleteVehicle(@PathVariable id: Long) {
        vehicleService.deleteVehicle(id)
    }

}