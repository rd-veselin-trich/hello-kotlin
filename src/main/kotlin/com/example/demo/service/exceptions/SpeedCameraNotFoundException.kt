package com.example.demo.service.exceptions

class SpeedCameraNotFoundException(override val message: String = MESSAGE) : RuntimeException(message) {

    companion object {
        private const val MESSAGE = "Speed camera not found!"
    }
}