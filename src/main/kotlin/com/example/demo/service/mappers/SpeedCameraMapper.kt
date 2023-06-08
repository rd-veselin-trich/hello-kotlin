package com.example.demo.service.mappers

import com.example.demo.entity.SpeedCamera
import com.example.demo.model.speedCamera.SpeedCameraDto
import org.mapstruct.Mapper
import org.springframework.stereotype.Component

@Mapper
@Component
interface SpeedCameraMapper {

    fun entityToDto(speedCamera: SpeedCamera): SpeedCameraDto

    fun dtoToEntity(speedCameraDto: SpeedCameraDto): SpeedCamera
}