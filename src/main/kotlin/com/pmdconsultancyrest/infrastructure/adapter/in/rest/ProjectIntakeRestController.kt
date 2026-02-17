package com.pmdconsultancyrest.infrastructure.adapter.`in`.rest

import com.pmdconsultancyrest.application.port.`in`.ProcessProjectIntakePort
import com.pmdconsultancyrest.infrastructure.adapter.`in`.rest.dto.ProjectIntakeRequest
import com.pmdconsultancyrest.infrastructure.adapter.`in`.rest.mapper.ProjectIntakeRequestMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger {}

/**
 * REST Controller - Input Adapter (Driving Adapter)
 *
 * Input adapters:
 * - Receive input from external systems
 * - Convert DTOs to domain models
 * - Call input ports with domain models
 * - Handle technical errors
 * - Should NOT contain business logic
 */
@RestController
@RequestMapping("/project-intakes")
@Tag(name = "Project Intake", description = "API for submitting project intake requests")
class ProjectIntakeRestController(
    private val processProjectIntakePort: ProcessProjectIntakePort,
    private val mapper: ProjectIntakeRequestMapper
) {

    @PostMapping
    @Operation(summary = "Submit a new project intake")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "202", description = "Project intake accepted for processing"),
            ApiResponse(responseCode = "400", description = "Invalid project intake data")
        ]
    )
    fun submitProjectIntake(
        @RequestBody request: ProjectIntakeRequest
    ): ResponseEntity<Void> {
        log.info { "Received project intake submission request for client: ${request.clientName}" }
        log.debug { "Project intake request details: $request" }

        try {
            val domainModel = mapper.toDomain(request)
            processProjectIntakePort.process(domainModel)
            log.info { "Successfully processed project intake for client: ${request.clientName}" }
            return ResponseEntity.status(HttpStatus.ACCEPTED).build()
        } catch (e: Exception) {
            log.error(e) { "Error processing project intake for client: ${request.clientName}" }
            throw e
        }
    }
}
