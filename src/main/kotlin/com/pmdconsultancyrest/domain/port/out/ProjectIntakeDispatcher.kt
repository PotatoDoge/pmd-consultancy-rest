package com.pmdconsultancyrest.domain.port.out

import com.pmdconsultancyrest.domain.model.ProjectIntakeModel

interface ProjectIntakeDispatcher {

    fun dispatch(projectIntake: ProjectIntakeModel)

}