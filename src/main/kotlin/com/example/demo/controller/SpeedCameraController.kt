package com.example.demo.controller

import com.example.demo.model.`speed-camera`.SpeedCameraDto
import com.example.demo.service.SpeedCameraService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/speed-cameras")
class SpeedCameraController(private val speedCameraService: SpeedCameraService) {


    @PostMapping
    fun createSpeedCamera(@RequestBody speedCameraDto: SpeedCameraDto): SpeedCameraDto {
        return speedCameraService.registerSpeedCamera(speedCameraDto)
    }

    @PatchMapping("/{id}")
    fun moveSpeedCamera(@PathVariable id: Long, @RequestBody speedCameraDto: SpeedCameraDto): SpeedCameraDto {
        return speedCameraService.moveSpeedCamera(id, speedCameraDto)
    }

    @DeleteMapping("/{id}")
    fun retireSpeedCamera(@PathVariable id: Long) {
        speedCameraService.retireSpeedCamera(id)
    }

}