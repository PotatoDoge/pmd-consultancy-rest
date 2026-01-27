package com.pmdconsultancyrest.domain.model

import com.pmdconsultancyrest.application.usecase.BudgetRange

data class ProjectIntakeModel (
    val clientName: String,
    val industry: String,
    val companySize: String,
    val context: String,
    val painPoints: List<String>,
    val budgetRange: BudgetRangeModel,
    val additionalNotes: String?,
    val email: String
)