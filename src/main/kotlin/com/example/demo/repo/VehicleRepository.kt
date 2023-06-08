package com.example.demo.repo

import com.example.demo.entity.Vehicle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface VehicleRepository : JpaRepository<Vehicle, Long> {

    fun findByPlate(plate: String): Optional<Vehicle>
}