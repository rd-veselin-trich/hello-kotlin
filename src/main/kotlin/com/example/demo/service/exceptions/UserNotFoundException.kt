package com.example.demo.service.exceptions

class UserNotFoundException(override val message: String = MESSAGE) : RuntimeException(message) {

    companion object {
        private const val MESSAGE = "User Not Found Exception."
    }
}