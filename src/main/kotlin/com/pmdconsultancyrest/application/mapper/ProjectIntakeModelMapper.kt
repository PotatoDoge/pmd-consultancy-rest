package com.pmdconsultancyrest.application.mapper

import com.pmdconsultancyrest.application.usecase.BudgetRange
import com.pmdconsultancyrest.application.usecase.ProjectIntake
import com.pmdconsultancyrest.domain.model.BudgetRangeModel
import com.pmdconsultancyrest.domain.model.ProjectIntakeModel
import org.springframework.stereotype.Component

@Component
class ProjectIntakeModelMapper {

    fun toDomainModel(projectIntake: ProjectIntake): ProjectIntakeModel {
        return ProjectIntakeModel(
            clientName = projectIntake.clientName,
            industry = projectIntake.industry,
            companySize = projectIntake.companySize,
            context = projectIntake.context,
            painPoints = projectIntake.painPoints,
            budgetRange = budgetRangeToDomainModel(projectIntake.budgetRange),
            additionalNotes = projectIntake.additionalNotes
        )
    }

    private fun budgetRangeToDomainModel(budgetRange: BudgetRange): BudgetRangeModel {
        return BudgetRangeModel(
            min = budgetRange.min,
            max = budgetRange.max,
            currency = budgetRange.currency
        )
    }

}