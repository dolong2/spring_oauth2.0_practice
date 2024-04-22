package com.practice.oauth2.global.error.handler

import com.practice.oauth2.global.error.BasicException
import com.practice.oauth2.global.error.response.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.simpleName)

    @ExceptionHandler(BasicException::class)
    fun handleBasicException(request: HttpServletRequest, ex: BasicException): ResponseEntity<ErrorResponse> {
        log.error(request.method)
        log.error(request.requestURI)
        val errorCode = ex.errorCode
        log.error(errorCode.message)
        return ResponseEntity(ErrorResponse(errorCode), HttpStatus.valueOf(errorCode.status))
    }
}