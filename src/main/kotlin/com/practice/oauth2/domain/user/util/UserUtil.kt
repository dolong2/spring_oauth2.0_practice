package com.practice.oauth2.domain.user.util

import com.practice.oauth2.domain.user.entity.User
import com.practice.oauth2.domain.user.entity.repository.UserRepository
import com.practice.oauth2.domain.user.exception.UserNotFoundException
import com.practice.oauth2.global.security.auth.AuthDetails
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserUtil(
    private val userRepository: UserRepository
) {
    fun fetchCurrentUser(): User {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val id = if (principal is UserDetails) {
            (principal as AuthDetails).username
        } else {
            principal.toString()
        }
        return fetchUserById(id)
    }

    private fun fetchUserById(id: String): User =
        userRepository.findByIdOrNull(UUID.fromString(id)) ?: throw UserNotFoundException()



}