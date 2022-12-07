package com.practice.oauth2.infraStructure.config.security

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.stereotype.Component


@Component
@EnableWebSecurity
class SecurityConfiguration(
    //private val oauthService: OauthService
){
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            .csrf().disable()//csrf 공격을 막아주는 옵션을 disalbe, rest api같은 경우에는 브라우저를 통해 request 받지 않기 때문에 해당 옵션을 꺼도 됩니다.
            .headers().frameOptions().disable()
            .and()
            .logout().logoutSuccessUrl("/") //logout 요청시 홈으로 이동 - 기본 logout url = "/logout"
            .and()
            .oauth2Login() //OAuth2 로그인 설정 시작점
            .defaultSuccessUrl("/oauth/loginInfo", true) //OAuth2 성공시 redirect
            .userInfoEndpoint() //OAuth2 로그인 성공 이후 사용자 정보를 가져올 때 설정 담당
            //.userService(oAuthService)
        return http.build()
    }
}