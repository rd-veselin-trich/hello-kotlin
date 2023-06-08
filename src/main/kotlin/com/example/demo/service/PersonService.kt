package com.example.demo.service

import com.example.demo.model.users.PersonDto
import com.example.demo.repo.PersonRepository
import com.example.demo.service.exceptions.PersonNotFoundException
import com.example.demo.service.mappers.PersonMapper
import org.springframework.stereotype.Service

@Service
class PersonService(private val personRepository: PersonRepository, private val personMapper: PersonMapper) {

    fun registerPerson(personDto: PersonDto): PersonDto {
        return personMapper.entityToDto(personRepository.save(personMapper.dtoToEntity(personDto)))
    }

    fun findById(id: String): PersonDto {
        return personMapper.entityToDto(
            personRepository.findById(id).orElseThrow { PersonNotFoundException() })
    }
}