package com.practice.oauth2.domain.auth.presentation.data.response

import java.time.ZonedDateTime

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val accessExpiredTime: ZonedDateTime
)
