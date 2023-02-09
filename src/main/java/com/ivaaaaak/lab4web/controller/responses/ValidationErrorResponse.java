package com.ivaaaaak.lab4web.controller.responses;

import java.util.List;

public record ValidationErrorResponse (List<String> validationErrors) {}
