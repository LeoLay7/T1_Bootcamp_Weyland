package org.example.audit;

import lombok.Getter;

import java.util.Map;

@Getter
public class AuditEvent {
    private String methodName;
    private Map<String, Object> parameters;
    private Object result;
    private long timestamp;

    public AuditEvent(String methodName, Map<String, Object> parameters, Object result) {
        this.methodName = methodName;
        this.parameters = parameters;
        this.result = result;
        this.timestamp = System.currentTimeMillis();
    }
}
