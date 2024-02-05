package org.devleopx.study.spring.bean_lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[study][bean-lifecycle] 3. @PostConstruct, @PreDestroy 애노테이션 지원")
public class LifeCycleAnnotationTest {

    @Slf4j
    static class Client implements InitializingBean, DisposableBean {

        private String welcomeWord;

        public String welcomeWord() {
            return this.welcomeWord;
        }

        @PreDestroy
        public void destroy() throws Exception {
            log.info("goodbye LifeCycleAnnotationTest.class");
        }

        @PostConstruct
        public void afterPropertiesSet() throws Exception {
            this.welcomeWord = "안녕하세요";
        }
    }

    @Configuration
    static class AppConfig {
        @Bean
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
        assertThat(welcomeWord).isEqualTo("안녕하세요");

    }

}
