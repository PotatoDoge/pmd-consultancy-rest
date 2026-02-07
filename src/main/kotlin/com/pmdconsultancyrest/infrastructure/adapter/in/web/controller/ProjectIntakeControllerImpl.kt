package com.pmdconsultancyrest.infrastructure.adapter.`in`.web.controller

import com.pmdconsultancyrest.application.usecase.SubmitProjectIntakeUseCase
import com.pmdconsultancyrest.infrastructure.adapter.`in`.web.dto.ProjectIntakeRequest
import com.pmdconsultancyrest.infrastructure.adapter.`in`.web.openapi.ProjectIntakeController
import com.pmdconsultancyrest.infrastructure.adapter.mapper.ProjectIntakeMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

private val log = KotlinLogging.logger {}

@RestController
class ProjectIntakeControllerImpl(
    private val submitProjectIntakeUseCase: SubmitProjectIntakeUseCase,
    private val projectIntakeMapper: ProjectIntakeMapper
) : ProjectIntakeController {


    override fun submitProjectIntake(projectIntakeRequest: ProjectIntakeRequest): ResponseEntity<Void> {
        log.info { "Received project intake submission request" }
        log.debug { "${"Project intake request details: {}"} $projectIntakeRequest" }

        submitProjectIntakeUseCase.publishProjectIntake(projectIntakeMapper.toObject(projectIntakeRequest))

        log.info { "Project intake submitted successfully" }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null)
    }
}
