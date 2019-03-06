package test.kafka;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Factory
class KafkaTestConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTestConsumer.class);

    @KafkaListener(
            offsetReset = OffsetReset.EARLIEST
    )
    @Topic("test-topic")
    void testConsumer(@KafkaKey String key, String message) {
        LOGGER.info("Got message " + message);
    }
}
