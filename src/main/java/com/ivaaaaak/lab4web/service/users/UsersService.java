package com.ivaaaaak.lab4web.service.users;

import com.ivaaaaak.lab4web.controller.requests.AuthJwtRequest;
import com.ivaaaaak.lab4web.model.users.User;
import com.ivaaaaak.lab4web.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder encoder;

    public User addUser(final AuthJwtRequest request) {
        var newUser = new User();
        newUser.setUsername(request.username());
        newUser.setPassword(encoder.encode(request.password()));
        return usersRepository.save(newUser);
    }

    public boolean checkIfUserExists(final String username) {
        final var dbUser = usersRepository.findByUsername(username);
        return dbUser.isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<User> user = usersRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException("User not found");
    }
}
