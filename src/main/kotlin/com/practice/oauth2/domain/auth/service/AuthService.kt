package com.practice.oauth2.domain.auth.service

import com.practice.oauth2.domain.auth.entity.RefreshToken
import com.practice.oauth2.domain.auth.entity.repository.RefreshTokenRepository
import com.practice.oauth2.domain.auth.exception.AlreadyExistsUserException
import com.practice.oauth2.domain.auth.service.dto.extension.toEntity
import com.practice.oauth2.domain.auth.service.dto.request.SignInReqDto
import com.practice.oauth2.domain.auth.service.dto.request.SignupReqDto
import com.practice.oauth2.domain.auth.service.dto.response.TokenResDto
import com.practice.oauth2.domain.user.entity.repository.UserRepository
import com.practice.oauth2.domain.user.exception.UserNotFoundException
import com.practice.oauth2.global.jwt.exception.ExpiredTokenException
import com.practice.oauth2.global.jwt.exception.InvalidTokenException
import com.practice.oauth2.global.jwt.util.TokenGenerator
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenGenerator: TokenGenerator,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    @Transactional(rollbackFor = [Exception::class])
    fun signup(signupReqDto: SignupReqDto) {
        if (userRepository.existsByEmail(signupReqDto.email))
            throw AlreadyExistsUserException()

        val encodedPassword = passwordEncoder.encode(signupReqDto.password)

        val user = signupReqDto.toEntity(encodedPassword)
        userRepository.save(user)
    }

    @Transactional(rollbackFor = [Exception::class])
    fun signIn(signInReqDto: SignInReqDto): TokenResDto {
        val user = (userRepository.findByEmail(signInReqDto.email)
            ?: throw UserNotFoundException())

        val accessToken = tokenGenerator.generateAccessToken(user.id.toString())
        val refreshToken = tokenGenerator.generateRefreshToken(user.id.toString())
        val accessExpiredTime = tokenGenerator.getAccessExpiredTime()

        val refreshTokenEntity = RefreshToken(
            userId = user.id,
            userToken = refreshToken,
            expiredAt = tokenGenerator.getRefreshExpiredTime().toLocalDateTime()
        )
        refreshTokenRepository.save(refreshTokenEntity)

        return TokenResDto(accessToken, refreshToken, accessExpiredTime)
    }

    @Transactional(rollbackFor = [Exception::class])
    fun reissueToken(refreshToken: String): TokenResDto {
        val refreshTokenEntity = (refreshTokenRepository.findByIdOrNull(refreshToken)
            ?: throw InvalidTokenException())

        if (LocalDateTime.now().isAfter(refreshTokenEntity.expiredAt))
            throw ExpiredTokenException()

        val accessToken = tokenGenerator.generateAccessToken(refreshTokenEntity.userId.toString())
        val refreshToken = tokenGenerator.generateRefreshToken(refreshTokenEntity.userId.toString())
        val accessExpiredTime = tokenGenerator.getAccessExpiredTime()

        val newRefreshTokenEntity = RefreshToken(
            userId = refreshTokenEntity.userId,
            userToken = refreshToken,
            expiredAt = tokenGenerator.getRefreshExpiredTime().toLocalDateTime()
        )
        refreshTokenRepository.save(newRefreshTokenEntity)

        return TokenResDto(accessToken, refreshToken, accessExpiredTime)
    }
}