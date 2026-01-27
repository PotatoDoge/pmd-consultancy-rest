package com.pmdconsultancyrest.infrastructure.config

import com.pmdconsultancyrest.domain.port.`in`.RegisterProjectIntake
import com.pmdconsultancyrest.domain.port.out.ProjectIntakeDispatcher
import com.pmdconsultancyrest.domain.service.RegisterProjectIntakeService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DomainWiringConfig {

    @Bean
    fun registerProjectIntake(projectIntakeDispatcher: ProjectIntakeDispatcher): RegisterProjectIntake {
        return RegisterProjectIntakeService(projectIntakeDispatcher)
    }

}