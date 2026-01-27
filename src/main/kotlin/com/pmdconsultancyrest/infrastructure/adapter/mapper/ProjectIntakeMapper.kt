package com.pmdconsultancyrest.infrastructure.adapter.mapper

import com.pmdconsultancyrest.application.usecase.BudgetRange
import com.pmdconsultancyrest.application.usecase.ProjectIntake
import com.pmdconsultancyrest.infrastructure.adapter.`in`.web.dto.BudgetRangeRequest
import com.pmdconsultancyrest.infrastructure.adapter.`in`.web.dto.ProjectIntakeRequest
import org.springframework.stereotype.Service

@Service
class ProjectIntakeMapper {

    fun toObject(projectIntakeRequest: ProjectIntakeRequest): ProjectIntake {
        return ProjectIntake(
            clientName = projectIntakeRequest.clientName,
            industry = projectIntakeRequest.industry,
            companySize = projectIntakeRequest.companySize,
            context = projectIntakeRequest.context,
            painPoints = projectIntakeRequest.painPoints,
            budgetRange = budgetRangeRequestToObject(projectIntakeRequest.budgetRange),
            additionalNotes = projectIntakeRequest.additionalNotes,
            email = projectIntakeRequest.email
        )
    }

    private fun budgetRangeRequestToObject(budgetRange: BudgetRangeRequest): BudgetRange {
        return BudgetRange(
            min = budgetRange.min,
            max = budgetRange.max,
            currency = budgetRange.currency
        )
    }
}