package com.pmdconsultancyrest.infrastructure.adapter.`in`.rest

import com.pmdconsultancyrest.application.port.`in`.ProcessProjectIntakePort
import com.pmdconsultancyrest.infrastructure.adapter.`in`.rest.dto.ProjectIntakeRequest
import com.pmdconsultancyrest.infrastructure.adapter.`in`.rest.mapper.ProjectIntakeRequestMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
class ProjectIntakeRestController(
    private val processProjectIntakePort: ProcessProjectIntakePort,
    private val mapper: ProjectIntakeRequestMapper
) : ProjectIntakeRestApi {

    override fun submitProjectIntake(request: ProjectIntakeRequest): ResponseEntity<Void> {
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
