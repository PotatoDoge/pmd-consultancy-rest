package com.pmdconsultancyrest.domain.service

import com.pmdconsultancyrest.domain.model.ProjectIntake

/**
 * ProjectIntakeDomainService - Domain Service
 *
 * Domain services are used when:
 * - Business logic involves MULTIPLE domain objects
 * - Logic doesn't naturally belong to a single entity
 * - You need to coordinate between different domain models
 *
 * Domain services:
 * - Live in domain layer
 * - Are stateless
 * - Contain pure business logic
 * - Have NO infrastructure dependencies
 * - Work only with domain models
 */
class ProjectIntakeDomainService {

    /**
     * Business rule: Determine review team based on project characteristics
     */
    fun assignReviewTeam(projectIntake: ProjectIntake): String {
        return when {
            projectIntake.requiresSeniorReview() -> "SENIOR_TEAM"
            projectIntake.getCategory() == "PREMIUM" -> "PREMIUM_TEAM"
            projectIntake.getCategory() == "STANDARD" -> "STANDARD_TEAM"
            else -> "BASIC_TEAM"
        }
    }

    /**
     * Business rule: Check if project should be auto-approved for initial consultation
     */
    fun shouldAutoApproveConsultation(projectIntake: ProjectIntake): Boolean {
        return projectIntake.isComplete() &&
                !projectIntake.requiresSeniorReview() &&
                projectIntake.painPoints.size <= 3
    }

    /**
     * Business rule: Calculate priority score for queue ordering
     */
    fun calculatePriorityScore(projectIntake: ProjectIntake): Int {
        var score = 0

        // Budget-based scoring
        score += when (projectIntake.getCategory()) {
            "ENTERPRISE" -> 100
            "PREMIUM" -> 70
            "STANDARD" -> 40
            else -> 20
        }

        // Pain points scoring (urgency indicator)
        score += when {
            projectIntake.painPoints.size >= 5 -> 30
            projectIntake.painPoints.size >= 3 -> 20
            else -> 10
        }

        // Industry-specific scoring
        score += when (projectIntake.industry.uppercase()) {
            "FINANCE", "HEALTHCARE", "LEGAL" -> 15
            "TECHNOLOGY", "MANUFACTURING" -> 10
            else -> 5
        }

        return score
    }

    /**
     * Business rule: Determine if project requires compliance review
     */
    fun requiresComplianceReview(projectIntake: ProjectIntake): Boolean {
        val regulatedIndustries = setOf("FINANCE", "HEALTHCARE", "LEGAL", "GOVERNMENT")
        return projectIntake.industry.uppercase() in regulatedIndustries
    }
}
