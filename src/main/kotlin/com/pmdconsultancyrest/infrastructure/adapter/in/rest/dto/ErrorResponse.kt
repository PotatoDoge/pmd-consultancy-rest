package com.pmdconsultancyrest.infrastructure.adapter.`in`.rest.dto

import java.time.LocalDateTime

/**
 * Standard error response DTO
 *
 * Used to provide consistent error responses across the REST API
 */
data class ErrorResponse(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val message: String,
)
