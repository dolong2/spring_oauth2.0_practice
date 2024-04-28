package com.practice.oauth2.domain.auth.presentation

import com.practice.oauth2.domain.auth.presentation.data.extension.toDto
import com.practice.oauth2.domain.auth.presentation.data.extension.toResponse
import com.practice.oauth2.domain.auth.presentation.data.request.SignInRequest
import com.practice.oauth2.domain.auth.presentation.data.request.SignupRequest
import com.practice.oauth2.domain.auth.presentation.data.response.TokenResponse
import com.practice.oauth2.domain.auth.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
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

    @PostMapping
    fun signIn(@RequestBody signInRequest: SignInRequest): ResponseEntity<TokenResponse> =
        authService.signIn(signInRequest.toDto())
            .let { ResponseEntity.ok(it.toResponse()) }

    @PatchMapping("/reissue")
    fun reissue(@RequestHeader("refresh-token") refreshToken: String): ResponseEntity<TokenResponse> =
        authService.reissueToken(refreshToken)
            .let { ResponseEntity.ok(it.toResponse()) }
}