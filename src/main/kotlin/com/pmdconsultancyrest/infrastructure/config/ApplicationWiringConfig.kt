package com.pmdconsultancyrest.infrastructure.config

import com.pmdconsultancyrest.application.mapper.ProjectIntakeModelMapper
import com.pmdconsultancyrest.application.usecase.SubmitProjectIntakeUseCase
import com.pmdconsultancyrest.application.usecase.SubmitProjectIntakeUseCaseImpl
import com.pmdconsultancyrest.domain.port.`in`.RegisterProjectIntake
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationWiringConfig {

    @Bean
    fun submitProjectIntakeUseCase(
        registerProjectIntake: RegisterProjectIntake,
        mapper: ProjectIntakeModelMapper
    ): SubmitProjectIntakeUseCase {
        return SubmitProjectIntakeUseCaseImpl(registerProjectIntake, mapper)
    }

}