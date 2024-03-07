package tech.olatunbosun.wastemanagement.configs.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.olatunbosun.wastemanagement.configs.JwtService;
import tech.olatunbosun.wastemanagement.usermanagement.services.UserService;

import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
//    private final UserService userService;

//    @Autowired
//    public JwtAuthenticationFilter(JwtService jwtService,  UserDetailsService userDetailsService, @Qualifier("userServiceImpl") UserService userService) {
//        this.jwtService = jwtService;
//        this.userDetailsService = userDetailsService;
//        this.userService = userService;
//    }
//
//    @Bean
//    public JwtAuthenticationFilter jwtTokenFilter() {
//        return new JwtAuthenticationFilter(jwtService, userDetailsService, userService);
//    }
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        request.getHeader(HttpHeaders.AUTHORIZATION);
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String token;
        final String userEmail;
        if(isEmpty(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = header.split(" ")[1].trim();
         userEmail = jwtService.extractUsername(token);
         if (userEmail != null  && SecurityContextHolder.getContext().getAuthentication() == null) {
             filterChain.doFilter(request, response);
             return;
         }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
         if (jwtService.isTokenValid(token, userDetails)) {
             UsernamePasswordAuthenticationToken authenticationToken =
                     new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
             authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
             SecurityContextHolder.getContext().setAuthentication(authenticationToken);
         }
        filterChain.doFilter(request, response);


    }
}
//                userService.loadUserByUsername(userEmail);
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
//
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);