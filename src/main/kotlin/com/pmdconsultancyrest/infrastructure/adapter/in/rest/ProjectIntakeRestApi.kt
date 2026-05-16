package com.pmdconsultancyrest.infrastructure.adapter.`in`.rest

import com.pmdconsultancyrest.infrastructure.adapter.`in`.rest.dto.ProjectIntakeRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/project-intakes")
@Tag(name = "Project Intake", description = "API for submitting project intake requests")
interface ProjectIntakeRestApi {

    @PostMapping
    @Operation(summary = "Submit a new project intake")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "202", description = "Project intake accepted for processing"),
            ApiResponse(responseCode = "400", description = "Invalid project intake data")
        ]
    )
    fun submitProjectIntake(
        @RequestBody request: ProjectIntakeRequest
    ): ResponseEntity<Void>
}
