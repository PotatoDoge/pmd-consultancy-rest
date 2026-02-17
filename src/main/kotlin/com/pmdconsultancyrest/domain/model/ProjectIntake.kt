package com.pmdconsultancyrest.domain.model

import com.pmdconsultancyrest.domain.exception.InvalidProjectIntakeException

/**
 * ProjectIntake - Domain Model (Rich Domain Object)
 *
 * This is where BUSINESS LOGIC lives:
 * - Validation rules
 * - Business calculations
 * - State transitions
 * - Invariant enforcement
 *
 * Domain models are:
 * - Pure POJOs (no framework annotations)
 * - Immutable (val properties)
 * - Self-validating
 * - Framework-independent
 */
data class ProjectIntake(
    val clientName: String,
    val industry: String,
    val companySize: String,
    val context: String,
    val painPoints: List<String>,
    val budgetRange: BudgetRange,
    val additionalNotes: String? = null
) {

    // ========================================
    // BUSINESS LOGIC - Validation
    // ========================================

    /**
     * Validates business rules.
     * Called by application service before processing.
     */
    fun validate() {
        if (clientName.isBlank()) {
            throw InvalidProjectIntakeException("Client name is required")
        }
        if (industry.isBlank()) {
            throw InvalidProjectIntakeException("Industry is required")
        }
        if (companySize.isBlank()) {
            throw InvalidProjectIntakeException("Company size is required")
        }
        if (context.isBlank()) {
            throw InvalidProjectIntakeException("Context is required")
        }
        if (painPoints.isEmpty()) {
            throw InvalidProjectIntakeException("At least one pain point is required")
        }

        // Validate nested value object
        budgetRange.validate()
    }

    // ========================================
    // BUSINESS LOGIC - Domain Behavior
    // ========================================

    /**
     * Business rule: Check if this is a high-priority project
     */
    fun isHighPriority(): Boolean {
        return budgetRange.min >= 100000 || painPoints.size >= 5
    }

    /**
     * Business rule: Determine project category based on budget
     */
    fun getCategory(): String {
        return when {
            budgetRange.min >= 500000 -> "ENTERPRISE"
            budgetRange.min >= 100000 -> "PREMIUM"
            budgetRange.min >= 25000 -> "STANDARD"
            else -> "BASIC"
        }
    }

    /**
     * Business rule: Check if project requires senior review
     */
    fun requiresSeniorReview(): Boolean {
        return isHighPriority() || getCategory() == "ENTERPRISE"
    }

    /**
     * Business rule: Calculate estimated response time in hours
     */
    fun getEstimatedResponseTime(): Int {
        return when (getCategory()) {
            "ENTERPRISE" -> 4
            "PREMIUM" -> 8
            "STANDARD" -> 24
            else -> 48
        }
    }

    /**
     * Business rule: Check if all required information is complete
     */
    fun isComplete(): Boolean {
        return clientName.isNotBlank() &&
                industry.isNotBlank() &&
                companySize.isNotBlank() &&
                context.isNotBlank() &&
                painPoints.isNotEmpty() &&
                budgetRange.isValid()
    }
}
