package tech.olatunbosun.wastemanagement;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {


    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("v1/user/register").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
