package com.pmdconsultancyrest.infrastructure.adapter.`in`.web.dto

data class BudgetRangeRequest(
    val min: Int,
    val max: Int,
    val currency: String
)