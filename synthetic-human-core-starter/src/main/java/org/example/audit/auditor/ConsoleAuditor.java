package org.example.audit.auditor;

public class ConsoleAuditor implements Auditor {
    public void audit(String message) {
        System.out.println(message);
    }
}
