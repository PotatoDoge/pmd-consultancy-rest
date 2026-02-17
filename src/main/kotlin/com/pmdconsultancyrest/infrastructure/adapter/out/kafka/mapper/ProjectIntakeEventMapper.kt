package com.pmdconsultancyrest.infrastructure.adapter.out.kafka.mapper

import com.pmdconsultancyrest.domain.model.BudgetRange
import com.pmdconsultancyrest.domain.model.ProjectIntake
import com.pmdconsultancyrest.infrastructure.adapter.out.kafka.dto.BudgetRangeEvent
import com.pmdconsultancyrest.infrastructure.adapter.out.kafka.dto.ProjectIntakeEvent
import org.springframework.stereotype.Component

/**
 * Mapper - Converts Domain Model to DTO
 *
 * Mappers:
 * - Live in infrastructure layer
 * - Convert between domain models and DTOs
 * - Are stateless
 * - Handle conversions for external systems
 */
@Component
class ProjectIntakeEventMapper {

    fun toEvent(domain: ProjectIntake): ProjectIntakeEvent {
        return ProjectIntakeEvent(
            clientName = domain.clientName,
            industry = domain.industry,
            companySize = domain.companySize,
            context = domain.context,
            painPoints = domain.painPoints,
            budgetRange = budgetRangeToEvent(domain.budgetRange),
            additionalNotes = domain.additionalNotes
        )
    }

    private fun budgetRangeToEvent(domain: BudgetRange): BudgetRangeEvent {
        return BudgetRangeEvent(
            min = domain.min,
            max = domain.max,
            currency = domain.currency
        )
    }
}
