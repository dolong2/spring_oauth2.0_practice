package com.practice.oauth2.global.jwt.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("jwt.time")
class TokenTimeProperty(
    val accessTime: Long,
    val refreshTime: Long
)