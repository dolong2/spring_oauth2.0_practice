package com.practice.oauth2.domain.auth.service.dto.response

import java.time.ZonedDateTime

data class TokenResDto(
    val accessToken: String,
    val refreshToken: String,
    val accessExpires: ZonedDateTime
)
