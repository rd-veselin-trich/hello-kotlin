package com.example.demo.service.mappers

import com.example.demo.entity.Fine
import com.example.demo.model.fine.FineDto
import org.mapstruct.Mapper
import org.springframework.stereotype.Component

@Mapper
@Component
interface FineMapper {

    fun entityToDto(fine: Fine): FineDto

    fun dtoToEntity(fineDto: FineDto): Fine
}