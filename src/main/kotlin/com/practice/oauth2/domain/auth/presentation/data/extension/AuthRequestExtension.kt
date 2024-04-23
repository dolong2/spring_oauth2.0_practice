package com.practice.oauth2.domain.auth.presentation.data.extension

import com.practice.oauth2.domain.auth.presentation.data.request.SignupRequest
import com.practice.oauth2.domain.auth.service.dto.request.SignupReqDto

fun SignupRequest.toDto(): SignupReqDto =
    SignupReqDto(
        email = this.email,
        password = this.password,
        name = this.name,
        imageUrl = this.imageUrl
    )