package com.pmdconsultancyrest.application.usecase

data class ProjectIntake(
    val clientName: String,
    val industry: String,
    val companySize: String,
    val context: String,
    val painPoints: List<String>,
    val budgetRange: BudgetRange,
    val additionalNotes: String?
)