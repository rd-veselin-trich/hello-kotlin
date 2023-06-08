package com.example.demo.service

import com.example.demo.entity.Fine
import com.example.demo.entity.SpeedCamera
import com.example.demo.model.fine.FineDto
import com.example.demo.model.fine.FineIssueData
import com.example.demo.repo.FineRepository
import com.example.demo.service.mappers.FineMapper
import com.example.demo.service.mappers.SpeedCameraMapper
import com.example.demo.service.mappers.VehicleMapper
import org.springframework.stereotype.Service

@Service
class FineService(
    private val fineRepository: FineRepository,
    private val vehicleService: VehicleService,
    private val speedCameraService: SpeedCameraService,
    private val fineMapper: FineMapper,
    private val speedCameraMapper: SpeedCameraMapper,
    private val vehicleMapper: VehicleMapper
) {
    fun issueFine(fineData: FineIssueData): FineDto {
        val speedCamera: SpeedCamera = speedCameraMapper.dtoToEntity(speedCameraService.getCamera(fineData.cameraId))
        val vehicle = vehicleService.getVehicleByPlate(fineData.detectedVehiclePlate)

        val fine = Fine(
            null,
            speedCamera,
            vehicleMapper.dtoToEntity(vehicle),
            fineData.detectedSpeed,
            determineAmountToPay(fineData.detectedSpeed - speedCamera.speedLimit, speedCamera.inCity)
        )

        return fineMapper.entityToDto(fineRepository.save(fine))
    }

    private fun determineAmountToPay(speedDifference: Float, inCity: Boolean): Float {
        return when (speedDifference) {
            in 1.0f..4.0f -> 0.0f
            in 5.0f..9.0f -> if (inCity) 50.0f else 25.0f
            in 10.0f..19.0f -> if (inCity) 100.0f else 50.0f
            in 20.0f..29.0f -> if (inCity) 200.0f else 100.0f
            in 30.0f..39.0f -> if (inCity) 400.0f else 200.0f
            in 40.0f..49.0f -> if (inCity) 800.0f else 400.0f
            else -> 1000.0f
        }
    }
}