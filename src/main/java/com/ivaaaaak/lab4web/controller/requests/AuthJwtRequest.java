package com.ivaaaaak.lab4web.controller.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AuthJwtRequest(@NotNull (message = "Username can't be null")
                             @NotBlank (message = "Username can't be blank")
                             @Pattern (regexp = "^[a-zA-Z0-9_]{3,10}$", message = "Username can contain from 3 to 10 symbols [a-zA-Z0-9_]")
                             String username,
                             @NotNull (message = "Password can't be null")
                             @NotBlank (message = "Password can't be blank")
                             @Pattern (regexp = "^[a-zA-Z0-9_]*$", message = "Password can contain [a-zA-Z0-9_]")
                             String password) {}
