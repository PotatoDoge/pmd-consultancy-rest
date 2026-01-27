package com.pmdconsultancyrest.domain.model

data class BudgetRangeModel(
    val min: Int,
    val max: Int,
    val currency: String
)