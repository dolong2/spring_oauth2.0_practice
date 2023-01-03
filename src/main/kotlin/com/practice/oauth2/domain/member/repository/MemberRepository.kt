package com.practice.oauth2.domain.member.repository

import com.practice.oauth2.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface MemberRepository: JpaRepository<Member, Long> {
    fun findByEmailAndProvider(email: String, provider: String): Optional<Member>
    fun existsByEmailAndProvider(email: String, provider: String): Boolean
}