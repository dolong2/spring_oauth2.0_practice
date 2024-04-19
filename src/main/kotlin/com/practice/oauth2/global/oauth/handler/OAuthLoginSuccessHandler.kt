package com.practice.oauth2.global.oauth.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.practice.oauth2.domain.user.presentation.data.response.TokenResponse
import com.practice.oauth2.global.jwt.util.TokenGenerator
import com.practice.oauth2.global.oauth.CustomOAuthUser
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuthLoginSuccessHandler(
    private val objectMapper: ObjectMapper,
    private val tokenGenerator: TokenGenerator
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val oAuthUser = authentication.principal as CustomOAuthUser

        val accessToken = tokenGenerator.generateAccessToken(oAuthUser.userId.toString())
        val refreshToken = tokenGenerator.generateRefreshToken(oAuthUser.userId.toString())
        val accessExpiredTime = tokenGenerator.getAccessExpiredTime()

        val tokenResponse = TokenResponse(accessToken, refreshToken, accessExpiredTime)
        val result = objectMapper.writeValueAsString(tokenResponse)

        response.status = HttpStatus.OK.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = Charsets.UTF_8.name()
        response.writer.write(result)
    }

}