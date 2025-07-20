package org.example.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.audit.auditor.Auditor;
import org.example.audit.auditor.AuditorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommandAuditService {
    private AuditMode auditMode;
    private Auditor auditor;
    private ObjectMapper objectMapper;
    private AuditorFactory auditorFactory;

    public CommandAuditService(
            AuditorFactory auditorFactory,
            @Value("${audit.mode:console}") String propertiesAuditMode
    ) {
        objectMapper = new ObjectMapper();
        this.auditorFactory = auditorFactory;
        auditMode = AuditMode.valueOf(propertiesAuditMode);

        auditor = auditorFactory.createAuditor(auditMode);
    }

    public void logAuditEvent(String methodName, Map<String, Object> parameters, Object result) {
        try {
            AuditEvent event = new AuditEvent(methodName, parameters, result);
            String json = objectMapper.writeValueAsString(event);
            auditor.audit(json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
