package test.kafka;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Factory
class KafkaTestSuperConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTestSuperConsumer.class);

    @KafkaListener(
            clientId = "kafka-test-super-consumer",
            offsetReset = OffsetReset.EARLIEST
    )
    @Topic("test-topic-super")
    void testConsumer(@KafkaKey String key, String message) {
        LOGGER.info("Got message " + message);
    }
}
