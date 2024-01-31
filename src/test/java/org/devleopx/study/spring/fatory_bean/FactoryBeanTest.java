package org.devleopx.study.spring.fatory_bean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[study] FactoryBean 을 사용해서 빈등록 수행")
public class FactoryBeanTest {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("FactoryBean 으로 등록된 Message 객체를 조회할 수 있다.")
    void findBean() {

        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(System.out::println);
        Message message = applicationContext.getBean("message", Message.class);
        assertThat(message.getText())
                .isEqualTo("hello spring");
    }

    @Test
    @DisplayName("FactoryBean 자체를 조회하고 싶다면 빈 이름 앞에 '&' 을 입력한다.")
    void findFactoryBean() {

        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(System.out::println);
        Object object = applicationContext.getBean("&message");
        assertThat(object).isInstanceOf(MessageFactoryBean.class);
    }

    @TestConfiguration
    static class TestConfig {

        @Bean("message")
        public MessageFactoryBean messageFactoryBean() {
            return new MessageFactoryBean();
        }
    }
}
