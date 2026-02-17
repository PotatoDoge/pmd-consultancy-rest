package com.pmdconsultancyrest.domain.model

import com.pmdconsultancyrest.domain.exception.InvalidProjectIntakeException

/**
 * BudgetRange - Value Object
 *
 * Value objects:
 * - Are immutable
 * - Define equality by value, not identity
 * - Contain their own business validation
 * - Are framework-independent
 */
data class BudgetRange(
    val min: Int,
    val max: Int,
    val currency: String
) {

    // ========================================
    // BUSINESS LOGIC - Validation
    // ========================================

    /**
     * Validates budget range business rules
     */
    fun validate() {
        if (min < 0) {
            throw InvalidProjectIntakeException("Minimum budget cannot be negative")
        }
        if (max < 0) {
            throw InvalidProjectIntakeException("Maximum budget cannot be negative")
        }
        if (min > max) {
            throw InvalidProjectIntakeException("Minimum budget cannot be greater than maximum budget")
        }
        if (currency.isBlank()) {
            throw InvalidProjectIntakeException("Currency is required")
        }
        if (!isValidCurrency(currency)) {
            throw InvalidProjectIntakeException("Invalid currency code: $currency")
        }
    }

    // ========================================
    // BUSINESS LOGIC - Domain Behavior
    // ========================================

    /**
     * Business rule: Check if budget range is valid
     */
    fun isValid(): Boolean {
        return min >= 0 && max >= min && currency.isNotBlank() && isValidCurrency(currency)
    }

    /**
     * Business logic: Format budget range for display
     */
    fun formatForDisplay(): String {
        return "$currency ${String.format("%,d", min)} - ${String.format("%,d", max)}"
    }

    /**
     * Business rule: Get budget range category
     */
    fun getBudgetCategory(): String {
        return when {
            min >= 500000 -> "LARGE"
            min >= 100000 -> "MEDIUM"
            min >= 25000 -> "SMALL"
            else -> "MICRO"
        }
    }

    /**
     * Business rule: Calculate average budget
     */
    fun getAverageBudget(): Int {
        return (min + max) / 2
    }

    // ========================================
    // Private Helper Methods
    // ========================================

    private fun isValidCurrency(currency: String): Boolean {
        val validCurrencies = setOf("USD", "EUR", "GBP", "CAD", "AUD", "BRL")
        return currency in validCurrencies
    }
}
