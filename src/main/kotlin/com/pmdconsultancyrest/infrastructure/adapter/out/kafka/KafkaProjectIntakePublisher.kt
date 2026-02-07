package com.pmdconsultancyrest.infrastructure.adapter.out.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.pmdconsultancyrest.domain.model.ProjectIntakeModel
import com.pmdconsultancyrest.domain.port.out.ProjectIntakeDispatcher
import com.pmdconsultancyrest.infrastructure.adapter.mapper.ProjectIntakeEventMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class KafkaProjectIntakePublisher(
    private val kafkaTemplate: KafkaTemplate<String, ProjectIntakeEvent>,
    private val projectIntakeEventMapper: ProjectIntakeEventMapper,
    private val objectMapper: ObjectMapper
) : ProjectIntakeDispatcher {

    override fun dispatch(projectIntake: ProjectIntakeModel) {
        val projectIntakeEvent = projectIntakeEventMapper.toEvent(projectIntake)
        val eventJson = objectMapper.writeValueAsString(projectIntakeEvent)
        log.info { "Publishing project intake event: $eventJson" }
        // publish to kafka
    }
}