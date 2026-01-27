package com.pmdconsultancyrest.application.usecase

import com.pmdconsultancyrest.application.mapper.ProjectIntakeModelMapper
import com.pmdconsultancyrest.domain.port.`in`.RegisterProjectIntake

class SubmitProjectIntakeUseCaseImpl(
    private val registerProjectIntake: RegisterProjectIntake,
    private val projectIntakeModelMapper: ProjectIntakeModelMapper
): SubmitProjectIntakeUseCase {

    override fun publishProjectIntake(projectIntake: ProjectIntake) {
        registerProjectIntake.registerProjectIntake(projectIntakeModelMapper.toDomainModel(projectIntake))
    }
}