package org.example.audit.auditor;

import lombok.Setter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaAuditor implements Auditor {
    private KafkaProducer<String, String> kafkaProducer;
    private String topic;

    public KafkaAuditor(KafkaProducer<String, String> kafkaProducer, String topic) {
        this.kafkaProducer = kafkaProducer;
        this.topic = topic;
    }

    @Override
    public void audit(String message) {
        kafkaProducer.send(new ProducerRecord<>(
                topic,
                message
        ));
    }
}
