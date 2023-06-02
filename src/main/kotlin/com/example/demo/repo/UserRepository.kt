package com.example.demo.repo

import com.example.demo.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long>{

   fun findByEmail(email: String?): Optional<User>
}