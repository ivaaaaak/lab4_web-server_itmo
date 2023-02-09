package com.ivaaaaak.lab4web.controller.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AttemptRequest(@NotNull (message = "X value can't be null")
                             double x,
                             @NotNull (message = "Y value can't be null")
                             double y,
                             @NotNull (message = "R value can't be null")
                             @Min(value = 1, message = "R value must be greater or equal 1")
                             @Max(value = 5, message = "R value must be less or equal 5")
                             double r) {}
