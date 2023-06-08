package com.example.demo.entity

import jakarta.persistence.*

@Entity
data class Fine(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @ManyToOne
    val issuer: SpeedCamera,

    @OneToOne
    var detectedVehicle: Vehicle,

    val detectedSpeed: Float,

    val amountToPay: Float

)
