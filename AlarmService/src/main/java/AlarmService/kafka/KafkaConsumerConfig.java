package AlarmService.kafka;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
@Slf4j
public class KafkaConsumerConfig {

    @Value("${data.host}")
    String host;

    //채팅 컨슈머
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AlarmInfo> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AlarmInfo> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        //ContainerProperties prop = factory.getContainerProperties();
        //prop.setConsumerRebalanceListener(rebalanceListener());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, AlarmInfo> consumerFactory() {
        JsonDeserializer<AlarmInfo> deserializer = new JsonDeserializer<>(AlarmInfo.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        ImmutableMap<String, Object> config = ImmutableMap.<String, Object>builder()
                .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, host)
                .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
                .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer)
                .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
                .put("group.id", KafkaConstants.GROUP_ID)
                .build();

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

}