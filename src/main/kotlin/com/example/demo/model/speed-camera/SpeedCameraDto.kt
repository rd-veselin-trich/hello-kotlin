package com.example.demo.model.`speed-camera`

data class SpeedCameraDto(

    val id: Long,

    val latitude: Float,

    val longitude: Float,

    val manufacturer: String,

    val speedLimit: Int,

    val inCity: Boolean
)
