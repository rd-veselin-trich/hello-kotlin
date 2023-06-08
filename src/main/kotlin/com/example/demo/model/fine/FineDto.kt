package com.example.demo.model.fine

import com.example.demo.model.`speed-camera`.SpeedCameraDto
import com.example.demo.model.vehicle.VehicleDto

data class FineDto(

    val id: Long?,

    val issuer: SpeedCameraDto,

    val detectedVehicle: VehicleDto,

    val detectedSpeed: Float,

    val amountToPay: Float
)