package com.example.demo.model

import jakarta.validation.constraints.Email

data class NewUser(val name: String, @Email val email: String, val password: String) {
}