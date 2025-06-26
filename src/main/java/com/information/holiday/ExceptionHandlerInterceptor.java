package com.information.holiday;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionHandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerInterceptor.class);

    @Before("execution(public * GlobalExceptionHandler.*(..))")
    public void beforeHandleRuntimeException(JoinPoint joinPoint) {
        RuntimeException exception = (RuntimeException) joinPoint.getArgs()[0];
        
        logger.info("=== Exception Handler Interceptor ===");
        logger.info("Intercepted exception type: {}", exception.getClass().getSimpleName());
        logger.info("Exception message: {}", exception.getMessage(), exception);
        logger.info("Timestamp: {}", java.time.LocalDateTime.now());
        logger.info("Thread: {}", Thread.currentThread().getName());
    }
}