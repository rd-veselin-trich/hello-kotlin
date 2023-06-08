package com.example.demo.model.fine

data class FineIssueData(
    val detectedSpeed: Float,
    val cameraId: Long,
    val detectedVehiclePlate: String
)
