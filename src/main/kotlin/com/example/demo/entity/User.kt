package com.example.demo.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import lombok.*

@Entity
@Table(name = "app_user")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        var name: String,

        @Email
        @Column(name = "email", unique = true)
        var email: String,

        var password: String
) {


}