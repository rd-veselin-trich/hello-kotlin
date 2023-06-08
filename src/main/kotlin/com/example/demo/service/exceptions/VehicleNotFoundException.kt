package com.example.demo.service.exceptions

class VehicleNotFoundException(override val message: String = MESSAGE) : RuntimeException(message) {

    companion object {
        private const val MESSAGE = "Vehicle Not Found Exception."
    }
}