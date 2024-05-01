package com.practice.oauth2.global.oauth.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.novalink.simpleparkingserver.global.error.code.ErrorCode
import com.practice.oauth2.global.error.BasicException
import com.practice.oauth2.global.error.response.ErrorResponse
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.web.filter.OncePerRequestFilter

class OAuthExceptionFilter(
    private val objectMapper: ObjectMapper,
) : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(this::class.simpleName)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (ex: Exception) {
            when(ex) {
                is BasicException -> {
                    logErrorResponse(request, ex.errorCode)
                    writeErrorResponse(response, ex)
                }
                else -> {
                    val errorCode = ErrorCode.INTERNAL_ERROR
                    logErrorResponse(request, errorCode)
                    writeErrorResponse(response, BasicException(errorCode))
                }
            }
        }
    }

    private fun logErrorResponse(request: HttpServletRequest, errorCode: ErrorCode) {
        log.error(request.method)
        log.error(request.requestURI)
        log.error(errorCode.message)
        log.error("${errorCode.code}")
    }
    private fun writeErrorResponse(response: HttpServletResponse, exception: BasicException) {
        val errorCode = exception.errorCode
        val responseBody = objectMapper.writeValueAsString(ErrorResponse(errorCode))
        response.status = errorCode.code
        response.characterEncoding = Charsets.UTF_8.name()
        response.contentType = "application/json"
        response.writer.write(responseBody)
    }
}