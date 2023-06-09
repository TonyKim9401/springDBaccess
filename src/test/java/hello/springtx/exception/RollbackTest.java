package hello.springtx.exception;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class RollbackTest {

    @Autowired
    RollbackService service;

    @Test //롤백
    void runtimeException() {
        assertThatThrownBy(() -> service.runtimeException())
                .isInstanceOf(RuntimeException.class);
    }

    @Test //커밋
    void checkedException() throws MyException {
        assertThatThrownBy(() -> service.checkedException())
                .isInstanceOf(Exception.class);
    }

    @Test //롤백
    void rollbackFor() throws MyException {
        assertThatThrownBy(() -> service.rollbackFor())
                .isInstanceOf(Exception.class);
    }


    @TestConfiguration
    static class RollbackTestConfig{

        @Bean
        public RollbackService rollbackService(){
            return new RollbackService();
        }

    }

    @Slf4j
    static class RollbackService{

        //런타임 예외 발생: 롤백
        @Transactional
        public void runtimeException(){
            log.info("call runtimeException");
            throw new RuntimeException();
        }

        //체크 예외 발생: 커밋
        @Transactional
        public void checkedException() throws MyException {
            log.info("call checkedException");
            throw new MyException();
        }


        //체크 예외 rollbackFor 지정:롤백
        @Transactional(rollbackFor = MyException.class)
        public void rollbackFor() throws MyException {
            log.info("call rollbackFor");
            throw new MyException();
        }
    }

    static class MyException extends Exception{
    }

}
