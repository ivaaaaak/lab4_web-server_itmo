package com.ivaaaaak.lab4web.repository;

import com.ivaaaaak.lab4web.model.users.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(@NotBlank @NotNull @Pattern(regexp = "^[a-zA-Z0-9_]{3,10}$") String username);
}
