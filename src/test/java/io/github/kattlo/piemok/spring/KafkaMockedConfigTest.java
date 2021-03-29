package io.github.kattlo.piemok.spring;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;

@Import(KafkaMockedConfig.class)
@SpringBootTest(
    classes = {
        App.class
    }
)
public class KafkaMockedConfigTest {

    @Autowired
    DefaultKafkaConsumerFactory<String, Object> consumerFactory;

    @Autowired
    DefaultKafkaProducerFactory<String, Object> producerFactory;

    @Test
    public void should_load_mocked_consumer_as_default_factory() {

        assertTrue(consumerFactory instanceof KafkaMockedConsumerFactory);
    }


    @Test
    public void should_load_mocked_producer_as_default_factory() {

        assertTrue(producerFactory instanceof KafkaMockedProducerFactory);
    }

}
