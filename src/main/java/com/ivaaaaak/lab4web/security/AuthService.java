package com.ivaaaaak.lab4web.security;

import com.ivaaaaak.lab4web.controller.requests.AuthJwtRequest;
import com.ivaaaaak.lab4web.controller.responses.JwtResponse;
import com.ivaaaaak.lab4web.model.users.User;
import com.ivaaaaak.lab4web.security.jwt.JwtProvider;
import com.ivaaaaak.lab4web.service.users.UsersService;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final UsersService usersService;
    private final PasswordEncoder encoder;

    public JwtResponse login(@NonNull final AuthJwtRequest request) throws AuthException {
        try {
            final var dbUser = usersService.loadUserByUsername(request.username());
            if (encoder.matches(request.password(), dbUser.getPassword())) {
                final var accessToken = jwtProvider.generateAccessToken(dbUser);
                final var refreshToken = jwtProvider.generateRefreshToken(dbUser);
                return new JwtResponse(accessToken, refreshToken);
            }
            throw new AuthException("Wrong password");
        } catch (UsernameNotFoundException e) {
            throw new AuthException("No such user");
        }
    }

    public User register(@NonNull final AuthJwtRequest request) {
        return usersService.addUser(request);
    }

    public JwtResponse getNewAccessToken(@NonNull final String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final var username = jwtProvider.getRefreshClaims(refreshToken).getSubject();
            final var dbUser = usersService.loadUserByUsername(username);
            final var newAccessToken = jwtProvider.generateAccessToken(dbUser);
            return new JwtResponse(newAccessToken, null);
        }
        throw new AuthException("Invalid refresh JWT token");
    }

    public JwtResponse refresh(@NonNull final String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final var username = jwtProvider.getRefreshClaims(refreshToken).getSubject();
            final var dbUser = usersService.loadUserByUsername(username);
            final var newAccessToken = jwtProvider.generateAccessToken(dbUser);
            final var newRefreshToken = jwtProvider.generateRefreshToken(dbUser);
            return new JwtResponse(newAccessToken, newRefreshToken);
        }
        throw new AuthException("Invalid refresh JWT token");
    }

    public boolean checkIfUserExists(final String username) {
        return usersService.checkIfUserExists(username);
    }
}
