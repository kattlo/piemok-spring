package io.github.kattlo.piemok.spring;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;

@Import(MockedKafkaConfig.class)
@SpringBootTest(
    classes = {
        App.class
    }
)
public class MockedKafkaConfigTest {

    @Autowired
    DefaultKafkaConsumerFactory<String, Object> consumerFactory;

    @Autowired
    DefaultKafkaProducerFactory<String, Object> producerFactory;

    @Autowired
    MockedKafkaConsumerFactory<String, Object> consumerMockedFactory;

    @Autowired
    MockedKafkaProducerFactory<String, Object> producerMockedFactory;

    @Test
    public void should_load_mocked_consumer_as_default_factory() {

        assertTrue(consumerFactory instanceof MockedKafkaConsumerFactory);
    }


    @Test
    public void should_load_mocked_producer_as_default_factory() {

        assertTrue(producerFactory instanceof MockedKafkaProducerFactory);
    }

    @Test
    public void should_return_the_mocked_consumer() {

        var mocked = consumerMockedFactory.of("my-group.id");
        assertNotNull(mocked);
        assertTrue(mocked.isPresent());
    }

    @Test
    public void should_return_the_mocked_producer() {

        assertTrue(producerMockedFactory.producer().isPresent());
    }

    @Test
    public void should_reset_the_mocked_consumer() {

        var mocked = consumerMockedFactory.of("my-group.id");
        mocked.ifPresent(m -> {

            m.reset("my-topic", null, "some-value");

        });
    }
}
