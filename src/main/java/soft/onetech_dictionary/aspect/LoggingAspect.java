package soft.onetech_dictionary.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final WebUtility webUtility;

    @Before("execution(* soft.onetech_dictionary.controller.DictionaryController.*(..))")
    public void loggedUser() {
        String username = webUtility.getCurrentUsername();
        HttpServletRequest request = webUtility.getClientHttpServletRequest();

        log.info("{} make request to ip: {} and to url: {}",
                username,
                request.getRemoteAddr(),
                request.getRequestURL()
        );
    }




}
