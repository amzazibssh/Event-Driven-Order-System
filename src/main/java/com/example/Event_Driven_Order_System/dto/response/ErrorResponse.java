package com.example.Event_Driven_Order_System.dto.response;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, String message) {
}
