package io.github.kattlo.piemok.spring;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;


@SpringBootApplication
public class App {

    @Autowired
    KafkaTemplate<String, Object> producer;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @PostConstruct
    public void init() {
        producer.partitionsFor("topic");
    }

    @KafkaListener(
        id = "my-group.id",
        idIsGroup = true,
        topics = "my-topic-name"
    )
    public void listener(@Payload ConsumerRecord<String, Object> r) throws Exception {
        // ...

        System.out.println("consumed record " + r);

        // ...

    }
}
