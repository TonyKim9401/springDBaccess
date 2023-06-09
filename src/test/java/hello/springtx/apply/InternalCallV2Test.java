package hello.springtx.apply;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class InternalCallV2Test {

    @Autowired
    CallService service;

    //Proxy 적용 확인
    @Test
    void printProxy() {
        log.info("callService class = {}", service.getClass());
    }

    @Test
    void externalCallV2() {
        service.external();
    }

    @TestConfiguration
    static class InternalCallV1TestConfig{

        @Bean
        CallService service() {
            return new CallService(internalService());
        }

        @Bean
        InternalService internalService(){
            return new InternalService();
        }
    }

    @Slf4j
    @RequiredArgsConstructor
    static class CallService{

        //@Transactional이 적용된 Proxy 객체가 주입됨.
        private final InternalService internalService;

        public void external(){
            log.info("call external");
            printTxInfo();
            internalService.internal();
        }

        public void printTxInfo(){
            final boolean txActive = TransactionSynchronizationManager.isSynchronizationActive();
            log.info("tx active={}", txActive);
        }
    }

    @Slf4j
    static class InternalService{

        @Transactional
        public void internal(){
            log.info("call internal");
            printTxInfo();
        }

        public void printTxInfo(){
            final boolean txActive = TransactionSynchronizationManager.isSynchronizationActive();
            log.info("tx active={}", txActive);
        }

    }

}
