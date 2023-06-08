package com.example.demo.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
data class Person(

    @Id
    val id: String,

    @GeneratedValue
    @UuidGenerator
    val uuid: UUID?,

    val firstName: String,

    val lastName: String,

    val address: String
)
