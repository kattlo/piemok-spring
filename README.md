# 🥧 Piemok 🥬 Spring

Convenience lib to use Piemok within Spring projects.

- 💡 [See examples](./examples)
- [See Piemok](https://github.com/kattlo/piemok)

__Support:__

- Java 11+
- Apache Kafka® 2.6.0+
- Spring Boot 2.4.3
- Spring Kafka 2.6.6
- Consuming by Subscription

## Getting Started

1. Dependency

  - Gradle
    ```groovy
    repositories {
        // ...

        maven {
            url = uri('http://packages.confluent.io/maven/')
        }

        maven { url 'https://jitpack.io' }
    }

    dependencies {
	    testImplementation 'com.github.kattlo:piemok-spring:v0.12.0'
	}

    ```

  - Apache Maven®
    ```xml
    <repositories>
		<repository>
		    <id>confluent</id>
		    <url>http://packages.confluent.io/maven/</url>
		</repository>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

	<dependency>
        <scope>test</scope>
	    <groupId>com.github.kattlo</groupId>
	    <artifactId>piemok-spring</artifactId>
	    <version>v0.12.0</version>
	</dependency>
    ```

  - [See other options](https://jitpack.io/#kattlo/piemok-spring)

2. To test code that produces events
```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.apache.kafka.clients.producer.MockProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import io.github.kattlo.piemok.spring.MockedKafkaConfig;
import io.github.kattlo.piemok.spring.MockedKafkaProducerFactory;

@Import(MockedKafkaConfig.class)
@SpringBootTest
public class YourTest {

    @Autowired
    MockedKafkaProducerFactory<String, Object> mocked;

    // Tip: clean the mocked producer before each unit execution
    @BeforeEach
    public void beforeEach() {
        mocked.producer().ifPresent(MockProducer::clear);
    }

    @Test
    public void your_test_unit() {

        // returns Optional<MockProducer>
        var producer = mocked.producer();

        producer.ifPresent(p -> {

            // returns the the records produced by your code
            p.history();

            //TODO your assetions about the produced records
        });

    }
}

```

3. To test code that consumes topics by subscription
```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;

@Import(MockedKafkaConfig.class)
@SpringBootTest
public class YourTest {

    @Autowired
    MockedKafkaConsumerFactory<String, Object> mocked;

    @Test
    public void your_test_unit() {

        // returns Optional<MockedConsumer>
        var mocked = mocked.of("my-group.id");

        mocked.ifPresent(m -> {
            // reseting the consumer by adding new record
            m.reset("my-topic", null, "some-value");
        });

        /* Tip: perform some sleep and the listener will have time to consume
         * and process
         */
        Thread.sleep(Duration.ofSeconds(3).toMillis());

        //TODO your assertion about the result of consumed record above

    }
}
```
