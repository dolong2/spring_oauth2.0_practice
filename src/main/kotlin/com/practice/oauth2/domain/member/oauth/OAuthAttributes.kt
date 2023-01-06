package com.practice.oauth2.domain.member.oauth

import com.practice.oauth2.domain.member.Member
import com.practice.oauth2.domain.member.Role
import java.util.Collections

class OAuthAttributes(
    private val attributes: Map<String, Any>,
    private val nameAttributeKey: String,
    private val name: String,
    private val email: String,
    private val picture: String,
) {
    fun toEntity(): Member {
        return Member(
            name = name,
            email = email,
            roles = Collections.singletonList(Role.ROLE_MEMBER),
            provider = "",
            picture = picture,
        )
    }
    fun of(registrationId: String?, userNameAttributeName: String, attributes: Map<String, Any>): OAuthAttributes {
        return ofGoogle(userNameAttributeName, attributes)
    }

    private fun ofGoogle(userNameAttributeName: String, attributes: Map<String, Any>): OAuthAttributes {
        return OAuthAttributes(
            name = attributes["name"].toString(),
            email = attributes["email"].toString(),
            picture = attributes["picture"].toString(),
            attributes = attributes,
            nameAttributeKey = userNameAttributeName
        )
    }
}
