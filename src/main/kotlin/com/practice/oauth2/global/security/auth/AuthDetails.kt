package com.practice.oauth2.global.security.auth

import com.practice.oauth2.domain.user.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthDetails(
    private val user: User
) : UserDetails{
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        user.roles
            .map { role -> SimpleGrantedAuthority(role.name) }
            .toMutableList()

    override fun getPassword(): String = ""

    override fun getUsername(): String  = user.id.toString()

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}