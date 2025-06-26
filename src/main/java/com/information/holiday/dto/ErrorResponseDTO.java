package com.information.holiday.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record ErrorResponseDTO(
        int status,
        String error,
        String message,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp
) {
    public ErrorResponseDTO(int status, String error, String message) {
        this(status, error, message, LocalDateTime.now());
    }
}