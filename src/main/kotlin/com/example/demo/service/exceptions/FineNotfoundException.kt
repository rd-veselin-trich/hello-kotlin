package com.example.demo.service.exceptions

class FineNotfoundException(override val message: String = MESSAGE) : RuntimeException(message) {
    companion object {
        private const val MESSAGE = "Fine Not Found Exception."
    }
}
