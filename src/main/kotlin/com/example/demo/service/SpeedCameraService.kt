package com.example.demo.service

import com.example.demo.model.speedCamera.SpeedCameraDto
import com.example.demo.repo.SpeedCameraRepository
import com.example.demo.service.exceptions.SpeedCameraNotFoundException
import com.example.demo.service.mappers.SpeedCameraMapper
import org.springframework.stereotype.Service

@Service
class SpeedCameraService(
    private val speedCameraRepository: SpeedCameraRepository,
    private val speedCameraMapper: SpeedCameraMapper
) {

    fun registerSpeedCamera(speedCameraDto: SpeedCameraDto): SpeedCameraDto {
        return speedCameraMapper.entityToDto(speedCameraRepository.save(speedCameraMapper.dtoToEntity(speedCameraDto)))
    }

    fun moveSpeedCamera(id: Long, speedCamera: SpeedCameraDto): SpeedCameraDto {
        val cameraToMove = speedCameraRepository.findById(id).orElseThrow { SpeedCameraNotFoundException() }
        cameraToMove.latitude = speedCamera.latitude
        cameraToMove.longitude = speedCamera.longitude
        cameraToMove.speedLimit = speedCamera.speedLimit
        return speedCameraMapper.entityToDto(speedCameraRepository.save(cameraToMove))
    }

    fun retireSpeedCamera(id: Long) {
        speedCameraRepository.deleteById(id)
    }

    fun getCamera(cameraId: Long): SpeedCameraDto {
        return speedCameraMapper.entityToDto(
            speedCameraRepository.findById(cameraId).orElseThrow { SpeedCameraNotFoundException() })
    }

}
