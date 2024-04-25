package com.practice.oauth2.domain.auth.entity.repository

import com.practice.oauth2.domain.auth.entity.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RefreshTokenRepository : JpaRepository<RefreshToken, String> {
    fun deleteByUserId(userId: UUID)
}