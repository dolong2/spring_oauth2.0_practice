package com.practice.oauth2.global.oauth.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.novalink.simpleparkingserver.global.error.code.ErrorCode
import com.practice.oauth2.global.error.response.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component

@Component
class OAuthLoginFailureHandler(
    private val objectMapper: ObjectMapper
) : AuthenticationFailureHandler {
    private val log = LoggerFactory.getLogger(this::class.simpleName)

    override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
        val errorCode = ErrorCode.OAUTH_LOGIN_FAILURE
        log.error(request.method)
        log.error(request.requestURI)
        log.error(errorCode.message)
        val result = objectMapper.writeValueAsString(ErrorResponse(errorCode))
        response.status = errorCode.status
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = Charsets.UTF_8.name()
        response.writer.write(result)
    }
}