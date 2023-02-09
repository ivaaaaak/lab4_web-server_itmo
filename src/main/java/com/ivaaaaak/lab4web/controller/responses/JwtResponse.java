package com.ivaaaaak.lab4web.controller.responses;

import jakarta.validation.constraints.NotNull;

public record JwtResponse (@NotNull String accessToken,
                           @NotNull String refreshToken) {}
