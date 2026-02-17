package com.pmdconsultancyrest.infrastructure.adapter.out.kafka.dto

/**
 * DTO for Kafka serialization
 *
 * DTOs:
 * - Are framework-specific
 * - Are mutable if needed (for serialization frameworks)
 * - Are used only at adapter boundaries
 * - Are NEVER passed to domain layer
 */
data class ProjectIntakeEvent(
    val clientName: String,
    val industry: String,
    val companySize: String,
    val context: String,
    val painPoints: List<String>,
    val budgetRange: BudgetRangeEvent,
    val additionalNotes: String? = null
)
