package com.practice.oauth2.domain.user.entity.repository

import com.practice.oauth2.domain.user.entity.User
import com.practice.oauth2.domain.user.entity.enums.SocialType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
    fun findUserBySocialIdAndSocialType(socialId: String, socialType: SocialType): User?
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): User?
}