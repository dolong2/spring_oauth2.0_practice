package com.practice.oauth2.global.oauth.handler

import com.fasterxml.jackson.databind.ObjectMapper
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

        //TODO Token 생성하는 로직 추가
        val accessToken = tokenGenerator.generateAccessToken(oAuthUser.userId.toString())
        val refreshToken = tokenGenerator.generateRefreshToken(oAuthUser.userId.toString())
        val accessExpiredTime = tokenGenerator.getAccessExpiredTime()

        response.status = HttpStatus.OK.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = Charsets.UTF_8.name()
        //TODO ObjectMapper를 통해서 응답값 작성
    }

}