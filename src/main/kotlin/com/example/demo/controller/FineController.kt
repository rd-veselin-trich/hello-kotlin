package com.example.demo.controller

import com.example.demo.model.fine.FineDto
import com.example.demo.model.fine.FineIssueData
import com.example.demo.service.FineService
import com.example.demo.service.exceptions.SpeedCameraNotFoundException
import com.example.demo.service.exceptions.VehicleNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/fines")
class FineController(private val fineService: FineService) {

    @PostMapping
    fun createFine(@RequestBody fineData: FineIssueData): ResponseEntity<FineDto> {
        return try {
            ResponseEntity(fineService.issueFine(fineData), HttpStatus.CREATED)
        } catch (e: VehicleNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }catch (e: SpeedCameraNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }
}