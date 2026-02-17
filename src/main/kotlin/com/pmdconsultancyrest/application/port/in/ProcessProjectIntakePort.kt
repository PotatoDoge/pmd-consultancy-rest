package com.pmdconsultancyrest.application.port.`in`

import com.pmdconsultancyrest.domain.model.ProjectIntake

/**
 * Input Port - Defines use case interface for processing project intakes
 *
 * Input ports:
 * - Define what the application can do
 * - Use domain models in method signatures
 * - Are implemented by application services
 * - Are called by driving adapters (input adapters)
 */
interface ProcessProjectIntakePort {
    fun process(projectIntake: ProjectIntake)
}
