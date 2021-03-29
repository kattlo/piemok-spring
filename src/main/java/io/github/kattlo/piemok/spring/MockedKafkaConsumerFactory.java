package io.github.kattlo.piemok.spring;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import io.github.kattlo.piemok.MockedConsumer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fabiojose
 */
@Slf4j
public class MockedKafkaConsumerFactory<K, V>
extends
    DefaultKafkaConsumerFactory<K,V> {

    private Map<String, MockedConsumer<K, V>> consumers = new HashMap<>();

    public MockedKafkaConsumerFactory(Map<String, Object> configs) {
        super(configs);
    }

    @Override
    protected Consumer<K, V> createRawConsumer(Map<String, Object> configProps) {

        MockedConsumer<K, V> mocked = null;

        var groupId = (String)configProps.get(ConsumerConfig.GROUP_ID_CONFIG);
        log.info("> > > listener.id (group.id): {}", groupId);

        if(!consumers.containsKey(groupId)){

            mocked = MockedConsumer.forSubscribe();
            consumers.put(groupId, mocked);
            log.info("> > > new mocked consumer creater for {}", groupId);

        } else {
            log.info("> > > mocked consumer already exists for {}", groupId);
            throw new IllegalStateException("Consider to use id property at your @KafkaListener and idIsGroup=true");
        }

        return mocked.consumer();
	}

    public Optional<MockedConsumer<K, V>> of(String listenerId) {
        return Optional.ofNullable(consumers.get(listenerId));
    }
}
