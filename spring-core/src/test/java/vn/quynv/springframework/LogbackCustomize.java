package vn.quynv.springframework;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import java.util.Map;


@Slf4j
public class LogbackCustomize {
    @Test
    void test_MappedContext_In_Spring_Slf4j () {
        Map<String,String> copyOfMDC = MDC.getCopyOfContextMap();
        MDC.put("GIDO","La Cai GI");
        if(copyOfMDC==null) {
            log.warn("Mapped Context is null or empty. Return ..");
            return;
        }
        copyOfMDC.entrySet().forEach(entry -> {
            log.info("KEY: {} VALUE: {}",entry.getKey(), entry.getValue());
        });
    }
}
