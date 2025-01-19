package com.example.pov.pov.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
	UserDetailsService userDetailsService;
	
	@Bean
    protected BCryptPasswordEncoder getPassWordEncoder() {
        return new BCryptPasswordEncoder();
    }
	

	@Bean
	protected DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(getPassWordEncoder());
		return authProvider;
	}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/**","/h2-console/**").permitAll()
                .anyRequest().permitAll()
            )
            .formLogin(login -> login.loginPage("/iniciar-sesion")
                .usernameParameter("username")
                .loginProcessingUrl("/usuarios/login-post") 
                .defaultSuccessUrl("/", true) 
                .permitAll() //
            )
            .logout(logout -> logout
                .logoutUrl("/cerrar-sesion") 
                .logoutSuccessUrl("/")

            )
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));
        return http.build();
    }
}

