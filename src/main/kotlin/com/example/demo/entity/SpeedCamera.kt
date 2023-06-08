package com.example.demo.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class SpeedCamera(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var latitude: Float,

    var longitude: Float,

    val manufacturer: String,

    var speedLimit: Int,

    val inCity: Boolean
)
