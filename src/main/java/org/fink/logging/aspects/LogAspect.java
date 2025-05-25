package org.fink.logging.aspects;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.fink.logging.services.ILoggingAccessor;
import java.util.Arrays;

@Aspect
@RequiredArgsConstructor
public class LogAspect {
    private final Logger log = LoggerFactory.getLogger(LogAspect.class);
    private final ILoggingAccessor loggingAccessor;

    @Before("@annotation(org.fink.logging.aspects.annotations.Loggable)")
    public void logExecution(JoinPoint joinPoint) {
        Level level = loggingAccessor.getConvertedLevel();

        log.atLevel(level).log("The method was called: {}", joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            log.atLevel(level).log("Parameters: {}", Arrays.toString(joinPoint.getArgs()));
        }
    }

    @AfterThrowing(pointcut = "@annotation(org.fink.logging.aspects.annotations.ExceptionHandling)", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        Level level = loggingAccessor.getConvertedLevel();

        log.atLevel(level).log("An exception {} occurred in method {} ",
            exception.getClass(), joinPoint.getSignature().getName());
        log.atLevel(level).log("An exception message: {}", exception.getMessage());
    }

    @Around("@annotation(org.fink.logging.aspects.annotations.LogTracking)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Level level = loggingAccessor.getConvertedLevel();

        long executionStart = System.currentTimeMillis();

        Object proceeded = joinPoint.proceed();

        long executionEnd = System.currentTimeMillis();
        log.atLevel(level).log("Execution time: {} milliseconds", executionEnd - executionStart);

        return proceeded;
    }

    @AfterReturning(pointcut = "@annotation(org.fink.logging.aspects.annotations.LogAfterSuccess)", returning = "result")
    public void logAfterSuccess(JoinPoint joinPoint, Object result) {
        Level level = loggingAccessor.getConvertedLevel();

        log.atLevel(level).log("Method {} returned successfully with result: {}",
                joinPoint.getSignature().getName(), result);
    }

}
