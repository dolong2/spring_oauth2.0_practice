package com.practice.oauth2.domain.auth.presentation.data.request

data class SignupRequest(
    val email: String,
    val password: String,
    val name: String,
    val imageUrl: String
)
