package test.kafka;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller
public class IndexController {

    private final KafkaTestClient kafkaTestClient;
    private final KafkaTestSuperClient kafkaTestSuperClient;

    public IndexController(KafkaTestClient kafkaTestClient, KafkaTestSuperClient kafkaTestSuperClient) {
        this.kafkaTestClient = kafkaTestClient;
        this.kafkaTestSuperClient = kafkaTestSuperClient;
    }

    @Get("/test")
    public HttpResponse testMessage() {
        kafkaTestClient.publishEvent("key", "value");
        kafkaTestSuperClient.publishEvent("superKey", "superValue");
        return HttpResponse.ok("Published Message");
    }

    @Get()
    public HttpResponse index() {
        return HttpResponse.ok("use /test");
    }
}
