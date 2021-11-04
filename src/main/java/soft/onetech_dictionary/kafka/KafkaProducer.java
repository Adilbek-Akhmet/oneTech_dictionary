package soft.onetech_dictionary.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Log4j2
@RequiredArgsConstructor
@Configuration
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        log.info("Kafka Producer: " + message);
        this.kafkaTemplate.send("test", message);
    }
}
