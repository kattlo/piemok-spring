package io.github.kattlo.piemok.spring;

import java.util.Map;
import java.util.Optional;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;

import io.github.kattlo.piemok.MockedProducer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fabiojose
 */
@Slf4j
public class KafkaMockedProducerFactory<K, V>
extends
    DefaultKafkaProducerFactory<K, V> {

    private MockProducer<K, V> producer;

    public KafkaMockedProducerFactory(Map<String, Object> configs) {
        super(configs);
    }

    @Override
    protected Producer<K, V> createRawProducer(Map<String, Object> rawConfigs) {

        if(null== producer) {
            producer = MockedProducer.create();
            getPostProcessors().forEach(pp -> pp.apply(producer));

            log.info("> > > mocked producer created");
        }

		return producer;
	}

    public Optional<MockProducer<K, V>> producer() {
        return Optional.ofNullable(producer);
    }

}
