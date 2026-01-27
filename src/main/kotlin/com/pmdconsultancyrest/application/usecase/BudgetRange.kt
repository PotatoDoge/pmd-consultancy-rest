package com.pmdconsultancyrest.application.usecase

data class BudgetRange(
    val min: Int,
    val max: Int,
    val currency: String
)