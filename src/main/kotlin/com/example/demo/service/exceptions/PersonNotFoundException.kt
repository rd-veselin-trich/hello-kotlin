package com.example.demo.service.exceptions

class PersonNotFoundException(override val message: String = MESSAGE) : RuntimeException(message) {
    companion object {
        private const val MESSAGE = "Person Not Found Exception."
    }
}