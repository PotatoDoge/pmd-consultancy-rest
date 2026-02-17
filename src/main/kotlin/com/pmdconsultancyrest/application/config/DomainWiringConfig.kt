package com.pmdconsultancyrest.application.config

import com.pmdconsultancyrest.domain.service.ProjectIntakeDomainService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Domain Wiring Configuration
 *
 * Purpose:
 * - Wire domain services as Spring beans
 * - Keep domain layer framework-agnostic
 * - Domain services have NO Spring annotations
 */
@Configuration
class DomainWiringConfig {

    @Bean
    fun projectIntakeDomainService(): ProjectIntakeDomainService {
        return ProjectIntakeDomainService()
    }
}
