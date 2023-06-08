package com.example.demo.service.mappers

import com.example.demo.entity.Person
import com.example.demo.model.users.PersonDto
import org.mapstruct.Mapper
import org.springframework.stereotype.Component

@Mapper
@Component
interface PersonMapper {

    fun entityToDto(person: Person): PersonDto

    fun dtoToEntity(personDto: PersonDto): Person
}