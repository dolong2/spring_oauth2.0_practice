package com.practice.oauth2.domain.auth.service

import com.practice.oauth2.domain.auth.exception.AlreadyExistsUserException
import com.practice.oauth2.domain.auth.service.dto.extension.toEntity
import com.practice.oauth2.domain.auth.service.dto.request.SignupReqDto
import com.practice.oauth2.domain.user.entity.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional(rollbackFor = [Exception::class])
    fun signup(signupReqDto: SignupReqDto) {
        if (userRepository.existsByEmail(signupReqDto.email))
            throw AlreadyExistsUserException()

        val encodedPassword = passwordEncoder.encode(signupReqDto.password)

        val user = signupReqDto.toEntity(encodedPassword)
        userRepository.save(user)
    }
}