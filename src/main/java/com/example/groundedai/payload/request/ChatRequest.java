package com.example.groundedai.payload.request;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest(@NotBlank String message) { }
