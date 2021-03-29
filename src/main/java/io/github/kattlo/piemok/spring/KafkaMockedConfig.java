package io.github.kattlo.piemok.spring;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;

/**
 * @author fabiojose
 */
public class KafkaMockedConfig {

    @Bean
    @Primary
    public ConsumerFactory<?, ?> mockedConsumerFactory() {
        return new KafkaMockedConsumerFactory<>(Map.of());
    }

    @Bean
    @Primary
    public ProducerFactory<?, ?> mockedProducerFactory() {
        return new KafkaMockedProducerFactory<>(Map.of());
    }
}
