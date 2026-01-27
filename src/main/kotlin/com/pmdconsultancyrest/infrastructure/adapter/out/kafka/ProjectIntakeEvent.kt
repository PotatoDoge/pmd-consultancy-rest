package com.pmdconsultancyrest.infrastructure.adapter.out.kafka


data class ProjectIntakeEvent(
    val clientName: String,
    val industry: String,
    val companySize: String,
    val context: String,
    val painPoints: List<String>,
    val budgetRange: BudgetRangeEvent,
    val additionalNotes: String?,
    val email: String
) {
}