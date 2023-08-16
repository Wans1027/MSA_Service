package ChattingService.kafka;

import ChattingService.dto.ResponseMessageDto;
import com.google.common.collect.ImmutableMap;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    //채팅 프로듀서
    @Bean
    public ProducerFactory<String, ResponseMessageDto> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfiguration());
    }

    @Bean
    public Map<String, Object> kafkaProducerConfiguration() {
        return ImmutableMap.<String, Object>builder()
                .put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKER)
                .put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class)
                .put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class)
                .put("group.id", KafkaConstants.GROUP_ID)
                .build();
    }
    @Bean
    public KafkaTemplate<String, ResponseMessageDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    //멤버 프로듀서
    @Bean
    public ProducerFactory<String, String> memberProducerFactory() {
         Map<String, Object> config = ImmutableMap.<String, Object>builder()
                .put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaMemberConstants.KAFKA_BROKER)
                .put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class)
                .put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class)
                .put("group.id", KafkaMemberConstants.GROUP_ID)
                .build();

        return new DefaultKafkaProducerFactory<>(config);
    }
    @Bean
    public KafkaTemplate<String, String> memberKafkaTemplate() {
        return new KafkaTemplate<>(memberProducerFactory());
    }

    /*@Bean
    public ProducerFactory<String, String> memberProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaMemberConstants.KAFKA_BROKER);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, String> memberKafkaTemplate() {
        return new KafkaTemplate<>(memberProducerFactory());
    }*/


}
