package com.pmdconsultancyrest.infrastructure.adapter.mapper

import com.pmdconsultancyrest.application.usecase.BudgetRange
import com.pmdconsultancyrest.domain.model.BudgetRangeModel
import com.pmdconsultancyrest.domain.model.ProjectIntakeModel
import com.pmdconsultancyrest.infrastructure.adapter.out.kafka.BudgetRangeEvent
import com.pmdconsultancyrest.infrastructure.adapter.out.kafka.ProjectIntakeEvent
import org.springframework.stereotype.Component

@Component
class ProjectIntakeEventMapper {

    fun toEvent(projectIntakeModel: ProjectIntakeModel): ProjectIntakeEvent {
        return ProjectIntakeEvent(
            clientName = projectIntakeModel.clientName,
            industry = projectIntakeModel.industry,
            companySize = projectIntakeModel.companySize,
            context = projectIntakeModel.context,
            painPoints = projectIntakeModel.painPoints,
            budgetRange = budgetRangeToEvent(projectIntakeModel.budgetRange),
            additionalNotes = projectIntakeModel.additionalNotes,
            email = projectIntakeModel.email
        )
    }

    private fun budgetRangeToEvent(budgetRange: BudgetRangeModel): BudgetRangeEvent {
        return BudgetRangeEvent(
            min = budgetRange.min,
            max = budgetRange.max,
            currency = budgetRange.currency
        )
    }
    
}