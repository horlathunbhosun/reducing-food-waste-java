package tech.olatunbosun.wastemanagement.configs;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author olulodeolatunbosun
 * @created 08/03/2024/03/2024 - 19:21
 */

@Service
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType("application/json"); // Set content type to JSON
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Set the HTTP status code
        PrintWriter writer = response.getWriter();
        writer.println("{ \"message\": \"You are not authorized to access this resource\" }"); // Set the custom message
    }
}
