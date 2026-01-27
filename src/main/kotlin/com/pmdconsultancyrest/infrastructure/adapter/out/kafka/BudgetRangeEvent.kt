package com.pmdconsultancyrest.infrastructure.adapter.out.kafka

data class BudgetRangeEvent(
    val min: Int,
    val max: Int,
    val currency: String
) {
}