package soft.onetech_dictionary.kafka;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Log4j2
@Configuration
public class KafkaConsumer {

    @KafkaListener(topics = "test")
    public void consume(String message) {
        log.info("Kafka Consumer receive: " + message);
    }
}
