package com.practice.oauth2.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.practice.oauth2.global.error.filter.ExceptionFilter
import com.practice.oauth2.global.jwt.filter.JwtReqFilter
import com.practice.oauth2.global.jwt.util.TokenParser
import com.practice.oauth2.global.oauth.handler.OAuthLoginFailureHandler
import com.practice.oauth2.global.oauth.handler.OAuthLoginSuccessHandler
import com.practice.oauth2.global.oauth.service.CustomOAuthUserService
import com.practice.oauth2.global.security.CustomAccessDeniedHandler
import com.practice.oauth2.global.security.CustomAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.cors.CorsUtils

@Configuration
class SecurityConfig(
    private val objectMapper: ObjectMapper,
    private val tokenParser: TokenParser,
    private val oAuthLoginSuccessHandler: OAuthLoginSuccessHandler,
    private val oAuthLoginFailureHandler: OAuthLoginFailureHandler,
    private val customOAuthUserService: CustomOAuthUserService
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors {  }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }

        http.sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

        http.authorizeHttpRequests {
            it.requestMatchers(RequestMatcher { request ->
                CorsUtils.isPreFlightRequest(request)
            }).permitAll()

            // auth
            it.requestMatchers(HttpMethod.POST, "/auth/signup").permitAll()
            it.requestMatchers(HttpMethod.POST, "/auth").permitAll()
            it.requestMatchers(HttpMethod.PATCH, "/auth/reissue").permitAll()

            it.anyRequest().denyAll()
        }

        http.oauth2Login {
            it.successHandler(oAuthLoginSuccessHandler)
            it.failureHandler(oAuthLoginFailureHandler)
            it.userInfoEndpoint {
                it.userService(customOAuthUserService)
            }
        }

        http
            .addFilterBefore(ExceptionFilter(objectMapper), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(JwtReqFilter(tokenParser), UsernamePasswordAuthenticationFilter::class.java)

        http
            .exceptionHandling {
                it.accessDeniedHandler(CustomAccessDeniedHandler(objectMapper))
                it.authenticationEntryPoint(CustomAuthenticationEntryPoint(objectMapper))
            }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        BCryptPasswordEncoder()
}