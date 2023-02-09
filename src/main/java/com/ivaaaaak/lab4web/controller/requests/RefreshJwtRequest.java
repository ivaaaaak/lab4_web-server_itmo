package com.ivaaaaak.lab4web.controller.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RefreshJwtRequest(@NotNull @NotBlank
                                String refreshToken) {
}
