package TradeGoodsService.kafka;

import TradeGoodsService.kafka.constants.KafkaMemberConstants;
import com.google.common.collect.ImmutableMap;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Value("${data.host}")
    String host;


    //멤버 프로듀서
    @Bean
    public ProducerFactory<String, String> memberProducerFactory() {
         Map<String, Object> config = ImmutableMap.<String, Object>builder()
                .put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, host)
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

    //푸시알림 프로듀서
    @Bean
    public ProducerFactory<String, AlarmInfo> pushAlarmProducerFactory() {
        Map<String, Object> config = ImmutableMap.<String, Object>builder()
                .put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, host)
                .put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class)
                .put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class)
                .put("group.id", KafkaMemberConstants.GROUP_ID)
                .build();

        return new DefaultKafkaProducerFactory<>(config);
    }
    @Bean
    public KafkaTemplate<String, AlarmInfo> pushAlarmKafkaTemplate() {
        return new KafkaTemplate<>(pushAlarmProducerFactory());
    }
}
