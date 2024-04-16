package com.practice.oauth2.global.oauth.user

class KakaoOAuthUserInfo(
    attributes: Map<String, Any?>
): OAuthUserInfo(attributes) {
    override fun getId(): String {
        return attributes["id"] as? String ?: ""
    }

    override fun getEmail(): String {
        val kakaoAccount = attributes["kakao_account"] as? Map<String, Any?> ?: return ""

        return kakaoAccount["email"] as? String ?: ""
    }

    override fun getName(): String {
        val kakaoAccount = attributes["kakao_account"] as? Map<String, Any?> ?: return ""
        val kakaoProfile = kakaoAccount["profile"] as? Map<String, Any?> ?: return ""

        return kakaoProfile["nickname"] as? String ?: ""
    }

    override fun getImageUrl(): String {
        val kakaoAccount = attributes["kakao_account"] as? Map<String, Any?> ?: return ""
        val kakaoProfile = kakaoAccount["profile"] as? Map<String, Any?> ?: return ""

        return kakaoProfile["profile_image_url"] as? String ?: ""
    }
}