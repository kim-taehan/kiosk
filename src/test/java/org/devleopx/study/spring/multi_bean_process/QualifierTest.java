package org.devleopx.study.spring.multi_bean_process;


import lombok.extern.slf4j.Slf4j;
import org.devleopx.study.spring.multi_bean_process.business.Client;
import org.devleopx.study.spring.multi_bean_process.business.Connection;
import org.devleopx.study.spring.multi_bean_process.business.MainConnection;
import org.devleopx.study.spring.multi_bean_process.business.SubConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(classes = {QualifierTest.App.class})
@DisplayName("2개의 빈이 등록되는 경우 @Qualifier로 직접 매칭을 할 수 있다.")
public class QualifierTest {

    @Configuration
    static class App {
        @Bean
        @Qualifier("mainConnection")
        public Connection mainConnection() {
            return new MainConnection();
        }
        @Bean
        public Connection subConnection() {
            return new SubConnection();
        }
        @Bean
        public Client client(@Qualifier("mainConnection") Connection connection) {
            return new Client(connection);
        }
    }

    @Autowired
    private Client client;

    @Test
    void run(){

        // given && when
        String ret = client.run();
        log.info("ret = {}", ret);

        // then
        assertThat(ret).isEqualTo("main connection");
    }


}
