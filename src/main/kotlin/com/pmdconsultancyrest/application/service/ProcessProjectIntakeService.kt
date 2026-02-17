package com.pmdconsultancyrest.application.service

import com.pmdconsultancyrest.application.port.`in`.ProcessProjectIntakePort
import com.pmdconsultancyrest.application.port.out.ProjectIntakeEventPublisher
import com.pmdconsultancyrest.domain.model.ProjectIntake
import com.pmdconsultancyrest.domain.service.ProjectIntakeDomainService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

/**
 * Application Service - Orchestrates use case
 *
 * Application services:
 * - Implement input ports
 * - Orchestrate workflow between domain and output ports
 * - Delegate business logic to domain layer
 * - Manage transaction boundaries
 * - Handle logging and monitoring
 * - NO business logic here - delegate to domain!
 */
@Service
class ProcessProjectIntakeService(
    // Inject output ports
    private val projectIntakeEventPublisher: ProjectIntakeEventPublisher,
    // Inject domain services
    private val projectIntakeDomainService: ProjectIntakeDomainService
) : ProcessProjectIntakePort {

    override fun process(projectIntake: ProjectIntake) {
        log.info { "Processing project intake for client: ${projectIntake.clientName}" }

        // Step 1: Validate domain rules (delegate to domain)
        projectIntake.validate()
        log.info { "Project intake validated successfully" }

        // Step 2: Execute domain logic (delegation to domain service)
        val category = projectIntake.getCategory()
        val priority = projectIntakeDomainService.calculatePriorityScore(projectIntake)
        val assignedTeam = projectIntakeDomainService.assignReviewTeam(projectIntake)
        val requiresCompliance = projectIntakeDomainService.requiresComplianceReview(projectIntake)

        log.info {
            "Project intake processed: category=$category, priority=$priority, " +
                    "team=$assignedTeam, requiresCompliance=$requiresCompliance"
        }

        // Step 3: TODO - Persist to database (when repository is implemented)
        // entityRepository.save(projectIntake)
        // log.info { "Project intake saved to repository" }

        // Step 4: Publish event via output port
        projectIntakeEventPublisher.publish(projectIntake)
        log.info { "Project intake event published successfully" }
    }
}
