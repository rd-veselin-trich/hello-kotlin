package com.example.demo.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email

@Entity
@Table(name = "app_user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Email
    @Column(name = "email", unique = true)
    var email: String,

    var password: String,

    @OneToOne
    var person: Person? = null
)