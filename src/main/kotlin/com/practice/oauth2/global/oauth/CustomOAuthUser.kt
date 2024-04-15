package com.practice.oauth2.global.oauth

import com.practice.oauth2.domain.user.entity.enums.Role
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import java.util.*

class CustomOAuthUser(
    roles: List<Role>,
    attributes: Map<String, Any>,
    nameAttributeKey: String,
    val userId: UUID
) : DefaultOAuth2User(
    roles.map { role -> SimpleGrantedAuthority(role.name) },
    attributes,
    nameAttributeKey
)