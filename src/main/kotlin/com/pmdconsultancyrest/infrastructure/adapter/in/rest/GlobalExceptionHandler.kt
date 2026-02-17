package com.pmdconsultancyrest.infrastructure.adapter.`in`.rest

import com.pmdconsultancyrest.domain.exception.InvalidProjectIntakeException
import com.pmdconsultancyrest.infrastructure.adapter.`in`.rest.dto.ErrorResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

private val log = KotlinLogging.logger {}

/**
 * Global Exception Handler - Infrastructure Layer
 *
 * This is an infrastructure concern (REST-specific error handling)
 * - Converts domain exceptions to HTTP responses
 * - Provides consistent error response format
 * - Logs exceptions appropriately
 * - Lives in infrastructure layer (not domain or application)
 *
 * Exception Handling Strategy:
 * - Domain exceptions (business rule violations) → 400 Bad Request
 * - Validation errors → 400 Bad Request
 * - Not found errors → 404 Not Found
 * - General exceptions → 500 Internal Server Error
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    /**
     * Handle domain business rule violations
     * Returns 400 Bad Request with the business rule violation message
     */
    @ExceptionHandler(InvalidProjectIntakeException::class)
    fun handleInvalidProjectIntakeException(
        ex: InvalidProjectIntakeException
    ): ResponseEntity<ErrorResponse> {
        log.warn { "Business rule violation: ${ex.message}" }

        val errorResponse = ErrorResponse(
            message = ex.message ?: "Invalid project intake data"
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    /**
     * Handle illegal argument exceptions (validation errors)
     * Returns 400 Bad Request
     */
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        ex: IllegalArgumentException
    ): ResponseEntity<ErrorResponse> {
        log.warn { "Validation error: ${ex.message}" }

        val errorResponse = ErrorResponse(
            message = ex.message ?: "Invalid request data",
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    /**
     * Handle all other exceptions
     * Returns 500 Internal Server Error
     * Logs the full stack trace for debugging
     */
    @ExceptionHandler(Exception::class)
    fun handleGlobalException(
        ex: Exception
    ): ResponseEntity<ErrorResponse> {
        log.error(ex) { "Unexpected error occurred: ${ex.message}" }

        val errorResponse = ErrorResponse(
            message = "An unexpected error occurred. Please try again later.",
        )

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }
}
