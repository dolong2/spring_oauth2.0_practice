package com.practice.oauth2.domain.user.entity.repository

import com.practice.oauth2.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
}