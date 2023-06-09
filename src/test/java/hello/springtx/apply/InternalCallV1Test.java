package hello.springtx.apply;

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
public class InternalCallV1Test {

    @Autowired
    CallService service;

    //Proxy 적용 확인
    @Test
    void printProxy() {
        log.info("callService class = {}", service.getClass());
    }

    //트랜잭셔널 적용 확인
    @Test
    void internalCall() {
        service.internal();
    }

    //내부 -> 내부(트랜잭셔널적용) 확인
    //트랜잭션이 적용되지 않음
    @Test
    void externalCall() {
        service.external();
    }

    @TestConfiguration
    static class InternalCallV1TestConfig{

        @Bean
        CallService service() {
            return new CallService();
        }

    }

    @Slf4j
    static class CallService{

        public void external(){
            log.info("call external");
            printTxInfo();
            internal();
        }

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
