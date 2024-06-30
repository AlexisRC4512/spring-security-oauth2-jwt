package com.aramos.auth.client.client_app.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        httpSecurity.authorizeHttpRequests((authHttp)->authHttp.requestMatchers(HttpMethod.GET,"/authorized").permitAll()
                .requestMatchers(HttpMethod.GET,"/list").hasAnyAuthority("SCOPE_read","SCOPE_write")
                .requestMatchers(HttpMethod.POST,"/create").hasAuthority("SCOPE_write")
                .anyRequest().authenticated()).csrf(csrf->csrf.disable())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(login->login.loginPage("/oauth2/authorization/client-app-alexis"))
                .oauth2Client(withDefaults())
                .oauth2ResourceServer(server->server.jwt(withDefaults()));
        return httpSecurity.build();
    }
}
