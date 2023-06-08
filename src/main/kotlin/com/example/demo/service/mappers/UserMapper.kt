package com.example.demo.service.mappers

import com.example.demo.entity.User
import com.example.demo.model.users.NewUser
import com.example.demo.model.users.UserResponse
import org.mapstruct.Mapper
import org.springframework.stereotype.Component


@Mapper
@Component
interface UserMapper {

    fun personToPersonDto(user: User): NewUser

    fun entityToResponse(user: User): UserResponse

}