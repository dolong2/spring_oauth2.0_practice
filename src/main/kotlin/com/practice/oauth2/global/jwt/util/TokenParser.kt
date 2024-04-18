package com.practice.oauth2.global.jwt.util

import com.practice.oauth2.global.jwt.exception.ExpiredTokenException
import com.practice.oauth2.global.jwt.exception.InvalidTokenException
import com.practice.oauth2.global.jwt.exception.InvalidTokenTypeException
import com.practice.oauth2.global.jwt.properties.TokenSecretProperty
import com.practice.oauth2.global.security.auth.AuthDetailsService
import io.jsonwebtoken.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.util.UUID

@Component
class TokenParser(
    private val tokenSecretProperty: TokenSecretProperty,
    private val authDetailsService: AuthDetailsService
) {
    private object JwtPrefix {
        const val ACCESS = "access"
        const val PREFIX = "Bearer "
    }

    fun parseTokenFromRequest(token: String): String? =
        if (token.startsWith(JwtPrefix.PREFIX)) token.substring(JwtPrefix.PREFIX.length)
        else null

    fun getAuthentication(token: String): Authentication {
        val claims = getClaims(tokenSecretProperty.accessSecret, token)

        if (claims.header[Header.JWT_TYPE] != JwtPrefix.ACCESS) throw InvalidTokenTypeException()

        val userId = extractIdFromAccessToken(token).toString()
        val userDetails = authDetailsService.loadUserByUsername(userId)

        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getRefreshTokenId(token: String): String =
        getClaims(tokenSecretProperty.refreshSecret, token)
            .body
            .id

    private fun extractIdFromAccessToken(accessToken: String): UUID {
        return UUID.fromString(getTokenId(accessToken, tokenSecretProperty.accessSecret))
    }

    private fun getTokenId(token: String, secret: Key): String =
        getClaims(secret, token)
            .body
            .id

    private fun getClaims(secret: Key, token: String): Jws<Claims> =
        try {
            Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
        } catch (e: Exception) {
            when(e) {
                is InvalidClaimException -> throw InvalidTokenException()
                is ExpiredJwtException -> throw ExpiredTokenException()
                is JwtException -> throw InvalidTokenException()
                else -> throw RuntimeException()
            }
        }

}