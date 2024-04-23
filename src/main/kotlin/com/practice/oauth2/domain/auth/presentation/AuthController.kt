package com.practice.oauth2.domain.auth.presentation

import com.practice.oauth2.domain.auth.presentation.data.extension.toDto
import com.practice.oauth2.domain.auth.presentation.data.request.SignupRequest
import com.practice.oauth2.domain.auth.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/signup")
    fun signup(@RequestBody signupRequest: SignupRequest): ResponseEntity<Void> =
        authService.signup(signupRequest.toDto())
            .run { ResponseEntity(HttpStatus.CREATED) }
}