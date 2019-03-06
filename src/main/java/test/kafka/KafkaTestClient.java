package test.kafka;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient(id = "kafka-test-client")
public interface KafkaTestClient {

    @Topic("test-topic")
    void publishEvent(@KafkaKey String eventKey, String body);
}