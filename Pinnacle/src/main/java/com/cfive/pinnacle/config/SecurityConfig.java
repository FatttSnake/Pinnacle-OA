package com.cfive.pinnacle.config;

import com.cfive.pinnacle.filter.JwtAuthenticationTokenFilter;
import com.cfive.pinnacle.handler.AccessDeniedHandler;
import com.cfive.pinnacle.handler.AuthenticationEntryPointHandler;
import com.cfive.pinnacle.service.permission.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {
    private UserDetailsServiceImpl userDetailsService;
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    private AuthenticationEntryPointHandler authenticationEntryPointHandler;
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setJwtAuthenticationTokenFilter(JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter) {
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
    }

    @Autowired
    public void setAuthenticationEntryPointHandler(AuthenticationEntryPointHandler authenticationEntryPointHandler) {
        this.authenticationEntryPointHandler = authenticationEntryPointHandler;
    }

    @Autowired
    public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setMaxAge(3600L);
        corsConfiguration.setAllowedOrigins(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                //  Disable CSRF
                .csrf()
                .disable()

                // Do not get SecurityContent by Session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                // Allow anonymous access
                .authorizeHttpRequests()
                .requestMatchers("/login")
                .anonymous()

                // Authentication required
                .anyRequest()
                .authenticated()
                .and()

                .logout()
                .disable()

                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPointHandler)
                .accessDeniedHandler(accessDeniedHandler)
                .and()

                .cors()
                .configurationSource(corsConfigurationSource())
                .and()

                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
