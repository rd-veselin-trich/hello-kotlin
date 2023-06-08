package com.example.demo.controller

import com.example.demo.model.vehicle.NewVehicle
import com.example.demo.model.vehicle.VehicleDto
import com.example.demo.service.VehicleService
import com.example.demo.service.exceptions.PersonNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/vehicles")
class VehicleController(private val vehicleService: VehicleService) {


    @PostMapping
    fun createVehicle(@RequestBody vehicle: NewVehicle): ResponseEntity<VehicleDto> {
        return try {
            ResponseEntity.status(201).body(vehicleService.registerVehicle(vehicle))
        } catch (e: PersonNotFoundException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteVehicle(@PathVariable id: Long) {
        vehicleService.deleteVehicle(id)
    }

}