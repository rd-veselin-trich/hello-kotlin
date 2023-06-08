package com.example.demo.repo

import com.example.demo.entity.SpeedCamera
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpeedCameraRepository : JpaRepository<SpeedCamera, Long> {
}