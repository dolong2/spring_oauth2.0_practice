package com.practice.oauth2.domain.auth.service.dto.request

data class SignupReqDto(
    val email: String,
    val password: String,
    val name: String,
    val imageUrl: String
)
