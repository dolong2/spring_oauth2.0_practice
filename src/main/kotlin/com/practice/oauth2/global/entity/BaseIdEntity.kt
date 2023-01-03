package com.practice.oauth2.global.entity

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

open class BaseIdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0
}