package com.practice.oauth2.global.oauth

import com.practice.oauth2.domain.user.entity.User
import com.practice.oauth2.domain.user.entity.enums.Role
import com.practice.oauth2.domain.user.entity.enums.SocialType
import com.practice.oauth2.global.oauth.user.GoogleOAuthUserInfo
import com.practice.oauth2.global.oauth.user.KakaoOAuthUserInfo
import com.practice.oauth2.global.oauth.user.NaverOAuthUserInfo
import com.practice.oauth2.global.oauth.user.OAuthUserInfo
import java.util.*

class OAuthAttributes(
    // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
    val nameAttributeKey: String,
    // 소셜 타입별 로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)
    val oauthUserInfo: OAuthUserInfo
) {
    /**
     * of메소드로 OAuthAttributes 객체가 생성되어, 유저 정보들이 담긴 OAuth2UserInfo가 소셜 타입별로 주입된 상태
     * OAuth2UserInfo에서 socialId(식별값), nickname, imageUrl을 가져와서 build
     * email에는 UUID로 중복 없는 랜덤 값 생성
     * role은 GUEST로 설정
     */
    fun toEntity(socialType: SocialType, oauth2UserInfo: OAuthUserInfo): User =
        User(
            id = UUID.randomUUID(),
            email = oauth2UserInfo.getEmail(),
            password = "",
            name = oauth2UserInfo.getName(),
            profileImgUrl = oauth2UserInfo.getImageUrl(),
            socialId = oauth2UserInfo.getId(),
            socialType = socialType,
            roles = listOf(Role.ROLE_USER)
        )

    companion object {
        /**
         * SocialType에 맞는 메소드 호출하여 OAuthAttributes 객체 반환
         * 파라미터 : userNameAttributeName -> OAuth2 로그인 시 키(PK)가 되는 값 / attributes : OAuth 서비스의 유저 정보들
         * 소셜별 of 메소드(ofGoogle, ofKaKao, ofNaver)들은 각각 소셜 로그인 API에서 제공하는
         * 회원의 식별값(id), attributes, nameAttributeKey를 저장 후 build
         */
        fun of(socialType: SocialType, userNameAttributeName: String, attributes: Map<String, Any?>): OAuthAttributes {
            return when(socialType) {
                SocialType.KAKAO -> {
                    ofKakao(userNameAttributeName, attributes)
                }
                SocialType.NAVER -> {
                    ofNaver(userNameAttributeName, attributes)
                }
                SocialType.GOOGLE -> {
                    ofGoogle(userNameAttributeName, attributes)
                }
                else -> {
                    throw RuntimeException()
                }
            }
        }

        private fun ofKakao(userNameAttributeName: String, attributes: Map<String, Any?>): OAuthAttributes =
            OAuthAttributes(
                userNameAttributeName,
                KakaoOAuthUserInfo(attributes)
            )

        private fun ofNaver(userNameAttributeName: String, attributes: Map<String, Any?>): OAuthAttributes =
            OAuthAttributes(
                userNameAttributeName,
                NaverOAuthUserInfo(attributes)
            )

        private fun ofGoogle(userNameAttributeName: String, attributes: Map<String, Any?>): OAuthAttributes =
            OAuthAttributes(
                userNameAttributeName,
                GoogleOAuthUserInfo(attributes)
            )
    }
}