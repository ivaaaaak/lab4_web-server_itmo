package com.ivaaaaak.lab4web.controller.responses;

import jakarta.validation.constraints.NotNull;

public record AttemptResponse(@NotNull Long id,
                              @NotNull double x,
                              @NotNull double y,
                              @NotNull double r,
                              @NotNull boolean hit) {}
