package com.practice.oauth2.global.security.auth

import com.practice.oauth2.domain.user.entity.repository.UserRepository
import com.practice.oauth2.domain.user.exception.UserNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*

@Component
class AuthDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService{
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = (userRepository.findByIdOrNull(UUID.fromString(username))
            ?: throw UserNotFoundException())
        return AuthDetails(user)
    }
}