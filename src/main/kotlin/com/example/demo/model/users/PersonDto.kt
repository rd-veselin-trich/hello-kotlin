package com.example.demo.model.users

import java.util.*

data class PersonDto(
    val uuid: UUID,

    val firstName: String,

    val lastName: String,

    val address: String,
)
