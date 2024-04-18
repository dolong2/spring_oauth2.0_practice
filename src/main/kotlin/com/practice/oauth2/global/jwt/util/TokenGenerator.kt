package com.practice.oauth2.global.jwt.util

import com.practice.oauth2.global.jwt.properties.TokenSecretProperty
import com.practice.oauth2.global.jwt.properties.TokenTimeProperty
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.security.Key
import java.time.ZonedDateTime
import java.util.*

@Component
class TokenGenerator(
    private val tokenSecretProperty: TokenSecretProperty,
    private val tokenTimeProperty: TokenTimeProperty
) {
    private object JwtPrefix {
        const val ACCESS = "access"
        const val REFRESH = "refresh"
    }

    fun generateAccessToken(userId: String): String =
        generateToken(userId, tokenSecretProperty.accessSecret, JwtPrefix.ACCESS, tokenTimeProperty.accessTime)

    fun generateRefreshToken(userId: String): String =
        generateToken(userId, tokenSecretProperty.refreshSecret, JwtPrefix.REFRESH, tokenTimeProperty.refreshTime)

    fun getAccessExpiredTime(): ZonedDateTime =
        ZonedDateTime.now().plusSeconds(tokenTimeProperty.accessTime / 1000)

    fun getRefreshExpiredTime(): ZonedDateTime =
        ZonedDateTime.now().plusSeconds(tokenTimeProperty.refreshTime / 1000)

    private fun generateToken(userId: String, secret: Key, type: String, exp: Long): String =
        Jwts.builder()
            .signWith(secret)
            .setHeaderParam(Header.JWT_TYPE, type)
            .setId(userId)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + exp))
            .compact()
}