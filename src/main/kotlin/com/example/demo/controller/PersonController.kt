package com.example.demo.controller

import com.example.demo.model.users.PersonDto
import com.example.demo.service.PersonService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/persons")
class PersonController(private val personService: PersonService) {

    @PostMapping
    fun createPerson(@RequestBody personDto: PersonDto): ResponseEntity<PersonDto> {
        return ResponseEntity(personService.registerPerson(personDto), HttpStatus.CREATED)
    }
}