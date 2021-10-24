package soft.onetech_dictionary.aspect;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
public class WordServiceAspect {

    @AfterThrowing(
            pointcut = "@annotation(soft.onetech_dictionary.aspect.ExceptionLogger)",
            throwing = "exception"
    )
    public void logAfterThrowing(Exception exception) {
        log.warn("Исключение {}", exception.toString());
    }

    @Around("@annotation(soft.onetech_dictionary.aspect.MethodLogger)")
    public void logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Метод {} начинает работу", joinPoint.getSignature().getName());
        joinPoint.proceed();
        log.info("Метод {} успешно закончил работу", joinPoint.getSignature().getName());
    }
}
