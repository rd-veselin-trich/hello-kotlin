package com.example.demo.controller

import com.example.demo.model.speedCamera.SpeedCameraDto
import com.example.demo.service.SpeedCameraService
import com.example.demo.service.exceptions.SpeedCameraNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/speed-cameras")
class SpeedCameraController(private val speedCameraService: SpeedCameraService) {


    @PostMapping
    fun createSpeedCamera(@RequestBody speedCameraDto: SpeedCameraDto): ResponseEntity<SpeedCameraDto> {
        return ResponseEntity.status(201).body(speedCameraService.registerSpeedCamera(speedCameraDto))
    }

    @PatchMapping("/{id}")
    fun moveSpeedCamera(
        @PathVariable id: Long,
        @RequestBody speedCameraDto: SpeedCameraDto
    ): ResponseEntity<SpeedCameraDto> {
        return try {
            ResponseEntity.ok(speedCameraService.moveSpeedCamera(id, speedCameraDto))
        } catch (e: SpeedCameraNotFoundException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun retireSpeedCamera(@PathVariable id: Long) {
        speedCameraService.retireSpeedCamera(id)
    }

}