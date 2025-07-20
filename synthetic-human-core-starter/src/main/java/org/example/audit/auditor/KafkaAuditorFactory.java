package org.example.audit.auditor;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.example.kafka.KafkaProducerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaAuditorFactory {
    private String auditTopic;
    private String servers;

    public KafkaAuditorFactory(
            @Value("${audit.servers}") String servers,
            @Value("${audit.topic}") String auditTopic
    ) {
        this.auditTopic = auditTopic;
        this.servers = servers;
    }

    public void setAuditTopic(String auditTopic) {this.auditTopic = auditTopic;}
    public void setServers(String servers) {this.servers = servers;}

    public Auditor createAuditor() {
        if (auditTopic == null || servers == null) {
            throw new IllegalArgumentException("auditTopic or servers not set");
        }
        KafkaProducer<String, String> kafkaProducer = KafkaProducerFactory.getKafkaProducer(servers);
        return new KafkaAuditor(kafkaProducer, auditTopic);
    }
}
