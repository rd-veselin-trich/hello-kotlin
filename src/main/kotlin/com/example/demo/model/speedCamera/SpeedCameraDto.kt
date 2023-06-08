package com.example.demo.model.speedCamera

data class SpeedCameraDto(

    val id: Long,

    val latitude: Float,

    val longitude: Float,

    val manufacturer: String,

    val speedLimit: Int,

    val inCity: Boolean
)
