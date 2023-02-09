package com.ivaaaaak.lab4web.security.jwt;

import com.ivaaaaak.lab4web.service.users.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UsersService usersService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = getTokenFromRequest(request);
        if (jwtToken != null && jwtProvider.validateAccessToken(jwtToken)) {
            final var claims = jwtProvider.getAccessClaims(jwtToken);
            final var user = usersService.loadUserByUsername(claims.getSubject());
            final var authentication = new JwtAuthentication();
            authentication.setUser(user);
            authentication.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(final HttpServletRequest request) {
        final String authHeader = request.getHeader(AUTHORIZATION);
        final String AUTH_PREFIX = "Bearer ";
        if (authHeader == null || !authHeader.startsWith(AUTH_PREFIX)) {
            return null;
        }
        return authHeader.substring(AUTH_PREFIX.length());
    }
}
