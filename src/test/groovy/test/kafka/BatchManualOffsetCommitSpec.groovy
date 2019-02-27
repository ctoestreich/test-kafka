package test.kafka

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.OffsetStrategy
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.configuration.kafka.config.AbstractKafkaConfiguration
import io.micronaut.context.ApplicationContext
import io.micronaut.core.util.CollectionUtils
import org.apache.kafka.clients.consumer.ConsumerRecord
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import javax.inject.Singleton

class BatchManualOffsetCommitSpec extends Specification {
    @Shared
    @AutoCleanup
    ApplicationContext context = ApplicationContext.run(
            CollectionUtils.mapOf(
                    "kafka.bootstrap.servers", 'localhost:${random.port}',
                    AbstractKafkaConfiguration.EMBEDDED, true,
                    AbstractKafkaConfiguration.EMBEDDED_TOPICS, ["test-topic"]
            )
    )

    void "test manual offset commit"() {
        given:
        ProductClient client = context.getBean(ProductClient)
        ProductListener listener = context.getBean(ProductListener)
        PollingConditions conditions = new PollingConditions(timeout: 30, delay: 1)

        when:
        client.send(new Product(name: "Apple"))
        client.send(new Product(name: "Orange"))

        then:
        conditions.eventually {
            listener.products.size() == 2
            listener.products.find() { it.name == "Apple" }
        }
    }

    @KafkaClient
    static interface ProductClient {

        @Topic("test-topic")
        void send(Product product)
    }

    @Singleton
    static class ProductListener {

        List<Product> products = []

        @KafkaListener(
                offsetReset = OffsetReset.EARLIEST,
                offsetStrategy = OffsetStrategy.AUTO
        )
        @Topic("test-topic")
        void receive(ConsumerRecord<String, Product> record) {
            this.products.add(record.value())
        }
    }

    static class Product {
        String name
    }
}