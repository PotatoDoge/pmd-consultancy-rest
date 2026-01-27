package com.pmdconsultancyrest.infrastructure.adapter.`in`.web.controller

import com.pmdconsultancyrest.application.usecase.SubmitProjectIntakeUseCase
import com.pmdconsultancyrest.infrastructure.adapter.`in`.web.dto.ProjectIntakeRequest
import com.pmdconsultancyrest.infrastructure.adapter.`in`.web.openapi.ProjectIntakeController
import com.pmdconsultancyrest.infrastructure.adapter.mapper.ProjectIntakeMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class ProjectIntakeControllerImpl(
    private val submitProjectIntakeUseCase: SubmitProjectIntakeUseCase,
    private val projectIntakeMapper: ProjectIntakeMapper
) : ProjectIntakeController {

    override fun submitProjectIntake(projectIntakeRequest: ProjectIntakeRequest): ResponseEntity<Void> {
        submitProjectIntakeUseCase.publishProjectIntake(projectIntakeMapper.toObject(projectIntakeRequest))
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null)
    }
}
