package org.devleopx.study.spring.application_context;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@DisplayName("[study] ApplicationContext Test")
public class ApplicationContextTest {

    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    interface DiscountPolicy {
    }

    static class RateDiscountPolicy implements DiscountPolicy {
    }

    static class FixedDiscountPolicy implements DiscountPolicy {
    }


    @Configuration
    static class AppConfig {

        @Bean
        public DiscountPolicy discountPolicy() {
            return new RateDiscountPolicy();
        }
    }

    @Configuration
    static class DuplicateConfig {

        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixedDiscountPolicy() {
            return new RateDiscountPolicy();
        }
    }

    @Test
    @DisplayName("등록된 모든 bean 출력하기")
    void findAllBean() {
        Optional<String> findBeanName = Arrays.stream(ac.getBeanDefinitionNames())
                .filter(s -> s.equals("discountPolicy"))
                .findAny();

        assertThat(findBeanName.get()).isEqualTo("discountPolicy");
    }

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        DiscountPolicy findBean = ac.getBean("discountPolicy", DiscountPolicy.class);

        assertThat(findBean).isInstanceOf(RateDiscountPolicy.class);
        assertThat(findBean.getClass().getName())
                .isEqualTo("org.devleopx.study.spring.application_context.ApplicationContextTest$RateDiscountPolicy");
    }

    @Test
    @DisplayName("빈 이름없이 타입으로 조회")
    void findBeanByType() {
        DiscountPolicy findBean = ac.getBean(DiscountPolicy.class);

        assertThat(findBean).isInstanceOf(RateDiscountPolicy.class);
        assertThat(findBean.getClass().getName())
                .isEqualTo("org.devleopx.study.spring.application_context.ApplicationContextTest$RateDiscountPolicy");
    }

    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByClassName() {
        DiscountPolicy findBean = ac.getBean("discountPolicy", RateDiscountPolicy.class);

        assertThat(findBean).isInstanceOf(RateDiscountPolicy.class);
        assertThat(findBean.getClass().getName())
                .isEqualTo("org.devleopx.study.spring.application_context.ApplicationContextTest$RateDiscountPolicy");
    }

    @Test
    @DisplayName("빈 이름으로 조회X")
    void findBeanByNameX() {
        assertThatThrownBy(() -> ac.getBean(FixedDiscountPolicy.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }


    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다")
    void findBeanByTypeDuplicate() {


        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DuplicateConfig.class);

        assertThatThrownBy(() -> applicationContext.getBean(DiscountPolicy.class))
                .isInstanceOf(NoUniqueBeanDefinitionException.class);
    }

    @Test
    @DisplayName("같은 타입이 둘 이상 있으면, 이름으로 조회할 수 있다")
    void findBeanByTypeDuplicateSkip() {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DuplicateConfig.class);
        DiscountPolicy findBean = applicationContext.getBean("rateDiscountPolicy", DiscountPolicy.class);

        assertThat(findBean).isInstanceOf(RateDiscountPolicy.class);
        assertThat(findBean.getClass().getName())
                .isEqualTo("org.devleopx.study.spring.application_context.ApplicationContextTest$RateDiscountPolicy");

    }

    @Test
    @DisplayName("특정타입이 2개 이상인 경우 조회하기")
    void findBeanByTypeMulti() {

        ApplicationContext duplicateAc = new AnnotationConfigApplicationContext(DuplicateConfig.class);
        Map<String, DiscountPolicy> beansOfType = duplicateAc.getBeansOfType(DiscountPolicy.class);

        assertThat(beansOfType).hasSize(2);
    }

    @Test
    @DisplayName("BeanDefinition을 조회할 수 있다")
    void findBeanDefinition() {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        BeanDefinition beanDefinition = applicationContext.getBeanDefinition("discountPolicy");

        assertThat(beanDefinition).isNotNull()
                .extracting("factoryBeanName", "factoryMethodName")
                .containsExactly("applicationContextTest.AppConfig", "discountPolicy");
    }

}
