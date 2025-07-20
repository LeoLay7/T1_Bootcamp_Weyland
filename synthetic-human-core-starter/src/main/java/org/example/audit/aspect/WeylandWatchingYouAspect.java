package org.example.audit.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.audit.CommandAuditService;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class WeylandWatchingYouAspect {
    private final CommandAuditService auditService;

    @Around("@annotation(org.example.audit.aspect.WeylandWatchingYou)")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodName = method.getDeclaringClass().getName() + "." + method.getName();

        Object[] args = joinPoint.getArgs();
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();

        Map<String, Object> parameters = new HashMap<>();
        if (paramNames != null) {
            for (int i = 0; i < args.length; i++) {
                parameters.put(paramNames[i], args[i]);
            }
        } else {
            // Если имена параметров недоступны, используем индексы
            for (int i = 0; i < args.length; i++) {
                parameters.put("arg" + i, args[i]);
            }
        }

        try {
            Object result = joinPoint.proceed();
            auditService.logAuditEvent(methodName, parameters, result);
            return result;
        } catch (Exception ex) {
            auditService.logAuditEvent(methodName, parameters, "Exception: " + ex.getMessage());
            throw ex;
        }
    }
}
