package org.devleopx.study.spring.bean_lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[study][bean_lifecycle] 2. 설정 정보에 초기화 메서드, 종료 메서드 지정로 lifecycle 관리")
public class LifeCycleBeanSettingTest {

    @Slf4j
    static class Client  {

        private String welcomeWord;

        public String welcomeWord() {
            return this.welcomeWord;
        }

        public void close() throws Exception {
            log.info("goodbye LifeCycleBeanSettingTest.class");
        }

        public void init() throws Exception {
            this.welcomeWord = "hi";
        }
    }

    @Configuration
    static class AppConfig {
        @Bean(destroyMethod = "close", initMethod = "init")
        public Client client() {
            return new Client();
        }
    }

    @Test
    void run(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        Client client = ac.getBean(Client.class);
        ac.close();
        String welcomeWord = client.welcomeWord();
        assertThat(welcomeWord).isEqualTo("hi");

    }

}
