package com.pmdconsultancyrest.infrastructure.adapter.`in`.web.dto

data class ProjectIntakeRequest(
    val clientName: String,
    val industry: String,
    val companySize: String,
    val context: String,
    val painPoints: List<String>,
    val budgetRange: BudgetRangeRequest,
    val additionalNotes: String?
)