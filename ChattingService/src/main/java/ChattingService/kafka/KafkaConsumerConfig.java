package ChattingService.kafka;

import ChattingService.dto.MessageDto;
import ChattingService.dto.ResponseMessageDto;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.*;

@EnableKafka
@Configuration
@Slf4j
public class KafkaConsumerConfig {



    //채팅 컨슈머
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ResponseMessageDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ResponseMessageDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        //ContainerProperties prop = factory.getContainerProperties();
        //prop.setConsumerRebalanceListener(rebalanceListener());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, ResponseMessageDto> consumerFactory() {
        JsonDeserializer<ResponseMessageDto> deserializer = new JsonDeserializer<>(ResponseMessageDto.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        ImmutableMap<String, Object> config = ImmutableMap.<String, Object>builder()
                .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKER)
                .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
                .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer)
                .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
                .put("group.id", KafkaConstants.GROUP_ID)
                .build();

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

    //멤버 컨슈머

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> memberKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(memberConsumerFactory());
        ContainerProperties prop = factory.getContainerProperties();
        prop.setConsumerRebalanceListener(rebalanceListener());
        return factory;
    }
    @Bean
    public ConsumerFactory<String, String> memberConsumerFactory() {

        ImmutableMap<String, Object> config = ImmutableMap.<String, Object>builder()
                .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaMemberConstants.KAFKA_BROKER)
                .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
                .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
                .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
                .put("group.id", KafkaMemberConstants.GROUP_ID)
                .build();

        return new DefaultKafkaConsumerFactory<>(config);
    }
    /*@Bean
    public ConsumerFactory<String, String> memberConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaMemberConstants.KAFKA_BROKER);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaMemberConstants.GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>
    memberKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(memberConsumerFactory());
        return factory;
    }*/


    //파티션 리스너
    @Bean
    public ConsumerAwareRebalanceListener rebalanceListener() {
        return new ConsumerAwareRebalanceListener() {
            @Override
            public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
                // here partitions
                //List<Integer> partList = new ArrayList<>();
                for (TopicPartition partition : partitions) {
                    int partition1 = partition.partition();
                    //partList.add(partition1);
                    log.info("사용중인파티션:{}", partition1);
                }
                //KafkaConstants.partitionList = partList;
            }
        };
    }

}