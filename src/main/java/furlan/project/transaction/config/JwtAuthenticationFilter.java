package furlan.project.transaction.config;

import furlan.project.transaction.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter responsible for intercepting every HTTP request to validate the JWT token.
 * It extracts the token from the Authorization header and sets the security context
 * if the token is valid.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        /**
         * 1. Validate the presence and format of the Authorization header.
         * If missing or not starting with "Bearer ", proceed to the next filter.
         */
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        /**
         * 2. Extract the token from the header (skipping the "Bearer " prefix).
         */
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        /**
         * 3. If a user is identified and not yet authenticated in the current context.
         */
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            /**
             * Note: In a production environment, you would typically load user details
             * from a database here. For this implementation, we validate token integrity.
             */
            if (jwtService.isTokenValid(jwt, userEmail)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userEmail,
                        null,
                        null // Authorities/Roles could be added here
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                /**
                 * 4. Update the Security Context Holder with the authenticated token.
                 */
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Pass the request along the filter chain
        filterChain.doFilter(request, response);
    }
}