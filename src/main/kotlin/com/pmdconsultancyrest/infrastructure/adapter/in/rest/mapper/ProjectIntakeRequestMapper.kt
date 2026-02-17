package com.pmdconsultancyrest.infrastructure.adapter.`in`.rest.mapper

import com.pmdconsultancyrest.domain.model.BudgetRange
import com.pmdconsultancyrest.domain.model.ProjectIntake
import com.pmdconsultancyrest.infrastructure.adapter.`in`.rest.dto.BudgetRangeRequest
import com.pmdconsultancyrest.infrastructure.adapter.`in`.rest.dto.ProjectIntakeRequest
import org.springframework.stereotype.Component

/**
 * Mapper - Converts DTO to Domain Model
 *
 * Mappers:
 * - Live in infrastructure layer
 * - Convert between DTOs and domain models
 * - Are stateless
 * - Handle null checks
 */
@Component
class ProjectIntakeRequestMapper {

    fun toDomain(request: ProjectIntakeRequest): ProjectIntake {
        return ProjectIntake(
            clientName = request.clientName,
            industry = request.industry,
            companySize = request.companySize,
            context = request.context,
            painPoints = request.painPoints,
            budgetRange = budgetRangeToDomain(request.budgetRange),
            additionalNotes = request.additionalNotes
        )
    }

    private fun budgetRangeToDomain(request: BudgetRangeRequest): BudgetRange {
        return BudgetRange(
            min = request.min,
            max = request.max,
            currency = request.currency
        )
    }
}
