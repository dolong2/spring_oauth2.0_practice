package com.practice.oauth2.global.oauth.service

import com.practice.oauth2.domain.user.entity.User
import com.practice.oauth2.domain.user.entity.enums.SocialType
import com.practice.oauth2.domain.user.entity.repository.UserRepository
import com.practice.oauth2.global.oauth.CustomOAuthUser
import com.practice.oauth2.global.oauth.OAuthAttributes
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuthUserService(
    private val userRepository: UserRepository
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private object OAuthPrefix {
        const val NAVER = "naver"
        const val KAKAO = "kakao"
    }
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val delegate = DefaultOAuth2UserService()
        val oAuthUser = delegate.loadUser(userRequest)

        val registrationId = userRequest.clientRegistration.registrationId
        val socialType = getSocialType(registrationId)
        val userNameAttributesName = userRequest
            .clientRegistration
            .providerDetails
            .userInfoEndpoint
            .userNameAttributeName
        val attributes = oAuthUser.attributes

        val extractAttributes = OAuthAttributes.of(socialType, userNameAttributesName, attributes)
        val user = getUser(extractAttributes, socialType)
        return CustomOAuthUser(
            user.roles,
            attributes,
            extractAttributes.nameAttributeKey,
            user.id
        )
    }

    private fun getSocialType(registrationId: String): SocialType =
        when(registrationId) {
            OAuthPrefix.KAKAO -> SocialType.KAKAO
            OAuthPrefix.NAVER -> SocialType.NAVER
            else -> throw RuntimeException()
        }

    private fun getUser(attributes: OAuthAttributes, socialType: SocialType): User {
        val socialId = attributes.oauthUserInfo.getId()
        if (userRepository.existsByEmailAndSocialTypeNot(attributes.oauthUserInfo.getEmail(), socialType))
            throw RuntimeException()
        return userRepository.findUserBySocialIdAndSocialType(socialId, socialType)
            ?: saveUser(attributes, socialType)
    }

    private fun saveUser(attributes: OAuthAttributes, socialType: SocialType): User {
        val user = attributes.toEntity(socialType, attributes.oauthUserInfo)
        return userRepository.save(user)
    }
}