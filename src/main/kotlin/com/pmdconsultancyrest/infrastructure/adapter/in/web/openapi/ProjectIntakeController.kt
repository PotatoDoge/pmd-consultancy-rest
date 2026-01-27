package com.pmdconsultancyrest.infrastructure.adapter.`in`.web.openapi

import com.pmdconsultancyrest.infrastructure.adapter.`in`.web.dto.ProjectIntakeRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Project Intake", description = "Project intake management API")
@RequestMapping("/project-intakes")
interface ProjectIntakeController {

    @Operation(
        summary = "Submit a new project intake",
        description = "Submits a new project intake request with the provided information"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "202",
                description = "Project intake accepted for processing",
                content = [Content()]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid request body",
                content = [Content()]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = [Content()]
            )
        ]
    )
    @PostMapping
    fun submitProjectIntake(@RequestBody projectIntakeRequest: ProjectIntakeRequest): ResponseEntity<Void>
}
