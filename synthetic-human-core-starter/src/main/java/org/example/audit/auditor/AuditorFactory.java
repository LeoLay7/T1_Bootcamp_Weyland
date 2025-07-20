package org.example.audit.auditor;

import org.example.audit.AuditMode;
import org.springframework.stereotype.Component;

@Component
public class AuditorFactory {
    private KafkaAuditorFactory kafkaAuditorFactory;

    public AuditorFactory(KafkaAuditorFactory kafkaAuditorFactory) {
        this.kafkaAuditorFactory = kafkaAuditorFactory;
    }

    public Auditor createAuditor(AuditMode auditMode) {
        if (auditMode == AuditMode.KAFKA) {
            return kafkaAuditorFactory.createAuditor();
        } else if (auditMode == AuditMode.CONSOLE) {
            return new ConsoleAuditor();
        }
        throw new IllegalArgumentException("Unsupported audit mode: " + auditMode);
    }
}
