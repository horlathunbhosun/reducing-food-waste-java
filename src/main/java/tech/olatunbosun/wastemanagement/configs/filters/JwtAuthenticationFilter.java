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
import tech.olatunbosun.wastemanagement.usermanagement.repository.TokenRepository;
import tech.olatunbosun.wastemanagement.usermanagement.services.UserService;

import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        request.getHeader(HttpHeaders.AUTHORIZATION);
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("Header: " + header);
        final String token;
        final String userEmail;
        if(isEmpty(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = header.split(" ")[1].trim();
        System.out.println("Token: " + token);
         userEmail = jwtService.extractUsername(token);
            System.out.println("User Email: " + userEmail);
        System.out.println("Security Context: " + SecurityContextHolder.getContext().getAuthentication());
         if (userEmail != null  && SecurityContextHolder.getContext().getAuthentication() == null) {
             UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
             System.out.println("UserDetails: " + userDetails);
             var isTokenValid = tokenRepository.findByToken(token)
                     .map(t -> !t.isExpired() && !t.isRevoked())
                     .orElse(false);
             System.out.println("Is Token Valid: " + isTokenValid);
             if (jwtService.isTokenValid(token, userDetails) && isTokenValid) {
                 UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                         userDetails,
                         null,
                         userDetails.getAuthorities()
                 );
                 authToken.setDetails(
                         new WebAuthenticationDetailsSource().buildDetails(request)
                 );
                 SecurityContextHolder.getContext().setAuthentication(authToken);
             }
             filterChain.doFilter(request, response);



         }
    }


//    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
//        var isTokenValid = tokenRepository.findByToken(jwt)
//                .map(t -> !t.isExpired() && !t.isRevoked())
//                .orElse(false);
//        if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
//            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                    userDetails,
//                    null,
//                    userDetails.getAuthorities()
//            );
//            authToken.setDetails(
//                    new WebAuthenticationDetailsSource().buildDetails(request)
//            );
//            SecurityContextHolder.getContext().setAuthentication(authToken);
//        }
//    }



//        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
//         if (jwtService.isTokenValid(token, userDetails)) {
//             UsernamePasswordAuthenticationToken authenticationToken =
//                     new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//             authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//             SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//         }
//        filterChain.doFilter(request, response);
}
