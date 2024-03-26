package com.ezen.management.config;

import com.ezen.management.security.CustomUserDetailService;
import com.ezen.management.security.handler.Custom403Handler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@Slf4j
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig {

    private final DataSource dataSource;
    private final CustomUserDetailService userDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        해당 메서드를 작성하면 필터를 커스텀할 수 있음

        http.authorizeHttpRequests(request ->
//                      권한이 없어도 접근할 수 있는 페이지
                        request.requestMatchers("/", "/student/**", "/member/login", "/css/**", "/img/**", "/js/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .exceptionHandling(exceptionHandler -> exceptionHandler.accessDeniedHandler(accessDeniedHandler()))
//                로그인 페이지를 커스텀 로그인 페이지로 매핑
//                defaultSuccessUrl 로그인 성공 시 이동하는 url
//                successForwardUrl은 성공시 이동하는 url이 아니므로 주의
                .formLogin(formLogin -> formLogin.loginPage("/member/login").loginProcessingUrl("/member/login").defaultSuccessUrl("/member"))
//                csrf 비활성화
                .csrf(AbstractHttpConfigurer::disable)
//                자동 로그인 처리
                .rememberMe(rememberMe -> rememberMe
                        .key("12345678")
                        .tokenRepository(persistentTokenRepository())
                        .userDetailsService(userDetailService)
                        .tokenValiditySeconds(60 * 60 * 24 * 30));//  유효 시간 30일;


        return http.build();

    }


    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
//        정적 리소스에 시큐리티 적용하지 않음
        return (web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }



//  스프링 시큐리티는 기본적으로 PasswordEncoder라는 존재를 필요로 함
//  BCryptPasswordEncoder는 가장 무난히 사용되는 패스워드 인코더
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


//  403 : 권한 없는 유저가 요청할 경우 발생하는 오류 -> 로그인으로 튕겨냄
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new Custom403Handler();
    }


}
