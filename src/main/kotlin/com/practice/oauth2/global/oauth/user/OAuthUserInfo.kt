package com.practice.oauth2.global.oauth.user

abstract class OAuthUserInfo(
    protected val attributes: Map<String, Any?>
) {
    abstract fun getId(): String

    abstract fun getEmail(): String

    abstract fun getName(): String

    abstract fun getImageUrl(): String
}