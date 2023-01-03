package com.practice.oauth2.domain.member.presentation.dto.req

import com.practice.oauth2.domain.member.Member
import com.practice.oauth2.domain.member.Role
import java.util.Collections

class SignupReqDto(
    val name: String,
    val email: String,
    val provider: String,
    val nickname: String,
){
    fun toEntity(): Member{
        return Member(
            email = email,
            name = name,
            provider = provider,
            nickname = nickname,
            roles = Collections.singletonList(Role.ROLE_MEMBER)
        )
    }
}