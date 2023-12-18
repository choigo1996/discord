package com.dw.discord.jwtauthority.config;


import com.dw.discord.jwtauthority.jwt.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.apache.tomcat.jni.SSLConf.apply;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	//토큰생성자
    private final TokenProvider tokenProvider;
    //토큰예외설정(EntryPoint : 인증실패/AccessDeniedHandler : 인가실패)
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(TokenProvider tokenProvider, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        super();
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        		//csrf비활성화
                .csrf(csrf -> csrf.disable())
                //예외처리
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                //Rest API 설정(인증과정을 거치지 않는 API등록)
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/api/authenticate").permitAll()
                        .requestMatchers("/api/signup").permitAll()
                        .requestMatchers("/api/basic/signup").permitAll()
                        .anyRequest().authenticated()
                )
                //세션관리(상태없음)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))
                //로그인 성공시,루트로 보냄(없어도 상관없음)
                .formLogin(formLoginCustomizer -> formLoginCustomizer.defaultSuccessUrl("/"))
                
                .addFilterBefore(
                        new JwtFilter(tokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
