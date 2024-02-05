package org.devleopx.study.spring.bean_scope;

import jakarta.inject.Provider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[bean_scope] singleton inner prototype scope process (java)")
public class PrototypeScopeProviderTest {

    @Scope("singleton")
    @RequiredArgsConstructor
    static class SingletonBean {
        private final Provider<PrototypeBean> provider;
        public int logic() {
            PrototypeBean prototypeBean = provider.get();
            prototypeBean.add();
            return prototypeBean.getValue();
        }
    }

    @Getter
    @Scope("prototype")
    static class PrototypeBean {
        private int value;
        public void add() {
            this.value++;
        }
    }

    @DisplayName("singleton 내부에 prototype bean에서도 매번 새로운 인스턴스를 제공한다.")
    @Test
    void singletonScope(){
        // given
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SingletonBean.class, PrototypeBean.class);

        // when
        SingletonBean beanA = applicationContext.getBean(SingletonBean.class);
        SingletonBean beanB = applicationContext.getBean(SingletonBean.class);

        // then
        assertThat(beanA.logic()).isEqualTo(1);
        assertThat(beanB.logic()).isEqualTo(1);
    }



}
