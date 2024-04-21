package com.practice.oauth2.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.novalink.simpleparkingserver.global.error.code.ErrorCode
import com.practice.oauth2.global.error.response.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

class CustomAccessDeniedHandler(
    private val objectMapper: ObjectMapper,
) : AccessDeniedHandler{
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException,
    ) {
        val errorCode = ErrorCode.INVALID_ROLE
        val result = objectMapper.writeValueAsString(ErrorResponse(errorCode))
        response.characterEncoding = Charsets.UTF_8.name()
        response.status = errorCode.status
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(result)
    }
}