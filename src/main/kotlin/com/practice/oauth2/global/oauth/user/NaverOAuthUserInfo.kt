package com.practice.oauth2.global.oauth.user

class NaverOAuthUserInfo(
    attributes: Map<String, Any?>
) : OAuthUserInfo(attributes = attributes["response"] as? Map<String, Any?> ?: mapOf(Pair("", ""))) {
    override fun getId(): String {
        return attributes["id"] as? String ?: ""
    }

    override fun getEmail(): String {
        return attributes["email"] as? String ?: ""
    }

    override fun getName(): String {
        return attributes["name"] as? String ?: ""
    }

    override fun getImageUrl(): String {
        return attributes["profile_image"] as? String ?: ""
    }
}