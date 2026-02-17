package com.pmdconsultancyrest.application.port.out

import com.pmdconsultancyrest.domain.model.ProjectIntake

/**
 * Output Port - Defines dependency interface for publishing project intake events
 *
 * Output ports:
 * - Define what the application needs from external systems
 * - Use domain models in method signatures
 * - Are implemented by driven adapters (output adapters)
 * - Are called by application services
 */
interface ProjectIntakeEventPublisher {
    fun publish(projectIntake: ProjectIntake)
}
