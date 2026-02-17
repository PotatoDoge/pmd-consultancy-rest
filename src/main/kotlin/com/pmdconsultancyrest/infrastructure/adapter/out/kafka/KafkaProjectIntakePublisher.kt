package com.pmdconsultancyrest.infrastructure.adapter.out.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.pmdconsultancyrest.application.port.out.ProjectIntakeEventPublisher
import com.pmdconsultancyrest.domain.model.ProjectIntake
import com.pmdconsultancyrest.infrastructure.adapter.out.kafka.dto.ProjectIntakeEvent
import com.pmdconsultancyrest.infrastructure.adapter.out.kafka.mapper.ProjectIntakeEventMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
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
    private val objectMapper: ObjectMapper,
    @Value("\${kafka.topics.project-intake}")
    private val topicName: String
) : ProjectIntakeEventPublisher {

    override fun publish(projectIntake: ProjectIntake) {
        log.info { "Publishing project intake to Kafka: ${projectIntake.clientName}" }

        // Convert domain to DTO
        val event = mapper.toEvent(projectIntake)

        val eventJson = objectMapper.writeValueAsString(event)
        log.debug { "Kafka event: $eventJson" }

        kafkaTemplate.send(topicName, projectIntake.clientName, event)
            .whenComplete { _, ex ->
                if (ex != null) {
                    log.error(ex) { "Failed to publish project intake: ${projectIntake.clientName}" }
                } else {
                    log.info { "Project intake published successfully to topic '$topicName': ${projectIntake.clientName}" }
                }
            }

        log.info { "Project intake published successfully: ${projectIntake.clientName}" }
    }
}
