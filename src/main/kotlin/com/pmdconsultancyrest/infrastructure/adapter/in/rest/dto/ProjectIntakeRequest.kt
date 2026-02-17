package com.pmdconsultancyrest.infrastructure.adapter.`in`.rest.dto

/**
 * DTO for REST API deserialization
 *
 * DTOs:
 * - Are framework-specific (can use annotations)
 * - Are mutable if needed
 * - Are used only at adapter boundaries
 * - Are NEVER passed to domain layer
 */
data class ProjectIntakeRequest(
    val clientName: String,
    val industry: String,
    val companySize: String,
    val context: String,
    val painPoints: List<String>,
    val budgetRange: BudgetRangeRequest,
    val additionalNotes: String? = null
)
