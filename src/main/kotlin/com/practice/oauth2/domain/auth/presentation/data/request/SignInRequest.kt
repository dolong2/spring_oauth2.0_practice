package com.practice.oauth2.domain.auth.presentation.data.request

data class SignInRequest(
    val email: String,
    val password: String
)