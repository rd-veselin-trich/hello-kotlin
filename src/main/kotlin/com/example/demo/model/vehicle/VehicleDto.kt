package com.example.demo.model.vehicle

import com.example.demo.model.users.PersonDto

data class VehicleDto(
    val id: Long?,

    val plate: String,

    val owner: PersonDto
)