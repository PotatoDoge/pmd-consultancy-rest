package com.pmdconsultancyrest.domain.service

import com.pmdconsultancyrest.domain.model.ProjectIntakeModel
import com.pmdconsultancyrest.domain.port.`in`.RegisterProjectIntake
import com.pmdconsultancyrest.domain.port.out.ProjectIntakeDispatcher

class RegisterProjectIntakeService(
    private val projectIntakeDispatcher: ProjectIntakeDispatcher
): RegisterProjectIntake {

    override fun registerProjectIntake(projectIntake: ProjectIntakeModel) {
        projectIntakeDispatcher.dispatch(projectIntake)
    }


}