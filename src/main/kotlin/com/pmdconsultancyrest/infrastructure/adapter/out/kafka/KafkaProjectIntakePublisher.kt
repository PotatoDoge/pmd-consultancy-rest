package com.pmdconsultancyrest.infrastructure.adapter.out.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.pmdconsultancyrest.application.port.out.ProjectIntakeEventPublisher
import com.pmdconsultancyrest.domain.model.ProjectIntake
import com.pmdconsultancyrest.infrastructure.adapter.out.kafka.dto.ProjectIntakeEvent
import com.pmdconsultancyrest.infrastructure.adapter.out.kafka.mapper.ProjectIntakeEventMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

/**
 * Kafka Publisher - Output Adapter (Driven Adapter)
 *
 * Output adapters:
 * - Implement output ports
 * - Convert domain models to DTOs
 * - Interact with external systems
 * - Handle connection and serialization details
 */
@Component
class KafkaProjectIntakePublisher(
    private val kafkaTemplate: KafkaTemplate<String, ProjectIntakeEvent>,
    private val mapper: ProjectIntakeEventMapper,
    private val objectMapper: ObjectMapper
) : ProjectIntakeEventPublisher {

    override fun publish(projectIntake: ProjectIntake) {
        log.info { "Publishing project intake to Kafka: ${projectIntake.clientName}" }

        // Convert domain to DTO
        val event = mapper.toEvent(projectIntake)

        // Log event for debugging
        val eventJson = objectMapper.writeValueAsString(event)
        log.debug { "Kafka event: $eventJson" }

        // TODO: Publish to Kafka when topic is configured
        // kafkaTemplate.send("project-intake-topic", projectIntake.clientName, event)

        log.info { "Project intake published successfully: ${projectIntake.clientName}" }
    }
}
