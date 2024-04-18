package com.practice.oauth2.global.jwt.filter

import com.practice.oauth2.global.jwt.util.TokenParser
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtReqFilter(
    private val tokenParser: TokenParser
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val token = resolveToken(request)
        token
            ?.run { SecurityContextHolder.getContext().authentication = tokenParser.getAuthentication(this) }
        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? =
        request.getHeader("Authorization")
            ?.let { rawToken -> tokenParser.parseTokenFromRequest(rawToken) }

}