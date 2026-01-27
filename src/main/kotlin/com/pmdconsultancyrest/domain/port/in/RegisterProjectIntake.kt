package com.pmdconsultancyrest.domain.port.`in`

import com.pmdconsultancyrest.domain.model.ProjectIntakeModel

interface RegisterProjectIntake {
    fun registerProjectIntake(projectIntake: ProjectIntakeModel)
}