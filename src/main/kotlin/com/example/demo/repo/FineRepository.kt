package com.example.demo.repo

import com.example.demo.entity.Fine
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FineRepository : JpaRepository<Fine, Long> {
}