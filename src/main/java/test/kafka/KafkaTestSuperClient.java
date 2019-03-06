package test.kafka;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient(id = "kafka-test-super-client")
public interface KafkaTestSuperClient {

    @Topic("test-topic-super")
    void publishEvent(@KafkaKey String eventKey, String body);
}