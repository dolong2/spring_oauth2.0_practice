package com.practice.oauth2.domain.auth.service.dto.extension

import com.practice.oauth2.domain.auth.service.dto.request.SignupReqDto
import com.practice.oauth2.domain.user.entity.User
import com.practice.oauth2.domain.user.entity.enums.Role
import com.practice.oauth2.domain.user.entity.enums.SocialType

fun SignupReqDto.toEntity(encodedPassword: String): User =
    User(
        email = this.email,
        password = encodedPassword,
        name = this.name,
        profileImgUrl = imageUrl,
        socialId = "",
        socialType = SocialType.ORIGINAL,
        roles = listOf(Role.ROLE_USER)
    )