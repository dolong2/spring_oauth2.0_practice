package com.practice.oauth2.domain.auth.presentation.data.extension

import com.practice.oauth2.domain.auth.presentation.data.response.TokenResponse
import com.practice.oauth2.domain.auth.service.dto.response.TokenResDto

fun TokenResDto.toResponse(): TokenResponse =
    TokenResponse(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        accessExpiredTime = this.accessExpires
    )