package com.example.demo.model.users

data class UserResponse(
    val id: Long?,
    val email: String,
    val person: PersonDto
)