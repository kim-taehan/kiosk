package org.devleopx.study.spring.bean_scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.*;

@DisplayName("[bean_scope] singleton vs prototype scope")
public class PrototypeScopeTest {

    @Scope("singleton")
    static class SingletonBean { }

    @Scope("prototype")
    static class PrototypeBean { }

    @DisplayName("singleton scope bean은 매번 동일한 인스턴스 반환")
    @Test
    void singletonScope(){
        // given
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SingletonBean.class);

        // when
        SingletonBean beanA = applicationContext.getBean(SingletonBean.class);
        SingletonBean beanB = applicationContext.getBean(SingletonBean.class);

        // then
        assertThat(beanA).isSameAs(beanB);
    }

    @DisplayName("prototype scope bean은 매번 새로운 인스턴스 반환")
    @Test
    void prototypeScope(){
        // given
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(PrototypeBean.class);

        // when
        PrototypeBean beanA = applicationContext.getBean(PrototypeBean.class);
        PrototypeBean beanB = applicationContext.getBean(PrototypeBean.class);

        // then
        assertThat(beanA).isNotSameAs(beanB);
    }

}
