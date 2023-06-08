package com.example.demo.model.users

import jakarta.validation.constraints.Email

data class NewUser(
    val name: String,
    @Email val email: String,
    val password: String,
    val personId: String
)