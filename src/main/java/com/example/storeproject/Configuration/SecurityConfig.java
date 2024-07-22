package com.example.storeproject.Configuration;

import com.example.storeproject.services.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DialectOverride;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;


@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig  {
  private final CustomUserDetailService customUserDetailService;
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests//определение правил авторизации для различиных http запросов
                        .requestMatchers("/", "/registration").permitAll()//разрешает доступ по этому адресу всем юзерам
                        .requestMatchers("/product/**", "/image/**")//указываем что по данному адресу мы будем требовать некоторые роли
                        .hasAnyAuthority("ROLE_ADMIN","ROLE_USER")//требуемые роли (вроде как можно объединить со след параметром которые для остальных действий тоже требует авторизации)
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean

      PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(8);
    }
}

