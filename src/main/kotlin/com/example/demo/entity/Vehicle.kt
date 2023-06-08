package com.example.demo.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
data class Vehicle(
    @Id
    val id: Long?,

    val plate: String,

    @ManyToOne
    @JoinColumn(name = "owner_id")
    var owner: Person
)
