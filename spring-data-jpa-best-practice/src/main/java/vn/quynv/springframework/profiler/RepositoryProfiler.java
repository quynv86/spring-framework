package vn.quynv.springframework.profiler;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RepositoryProfiler {

        @Pointcut("execution(public * org.springframework.data.repository.Repository+.*(..))")
        public void intercept(){}

        @Around("intercept()")
        public Object profile(ProceedingJoinPoint joinPoint) {
            long startMs = System.currentTimeMillis();
            Object result = null;
            try {
                result = joinPoint.proceed();
            }catch (Throwable e)
            {
                log.error(e.getMessage(), e);
            }
            long elapsedMs = System.currentTimeMillis() - startMs;
            log.info(joinPoint.getTarget() + "." + joinPoint.getSignature()+ ": Execute time: " + elapsedMs+" (ms) ");
            return result;
        }
}
