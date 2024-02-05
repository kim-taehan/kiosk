## Bean scope 란
> 지금까지 우리는 스프링 빈이 스프링 컨테이너의 시작과 함께 생성되어서 스프링 컨테이너가 종료될 때
까지 유지된다고 학습했다. 이것은 스프링 빈이 기본적으로 싱글톤 스코프로 생성되기 때문이다. 스코프는
번역 그대로 빈이 존재할 수 있는 범위를 뜻한다.

```text
싱글톤: 기본 스코프, 스프링 컨테이너의 시작과 종료까지 유지되는 가장 넓은 범위의 스코프이다.
프로토타입: 스프링 컨테이너는 프로토타입 빈의 생성과 의존관계 주입까지만 관여하고 더는 관리하지 않는
매우 짧은 범위의 스코프이다.
웹 관련 스코프
request: 웹 요청이 들어오고 나갈때 까지 유지되는 스코프이다.
session: 웹 세션이 생성되고 종료될 때 까지 유지되는 스코프이다.
application: 웹의 서블릿 컨텍스트와 같은 범위로 유지되는 스코프이다.
```

### 싱글톤 VS 프로토타입    
> 프로토 타입 빈은 생성과 의존성 주입까지만 스프링이 하고 이후에 일을 신경쓰지 않는다.

```java
@Scope("singleton")
static class SingletonBean { }

@Scope("prototype")
static class PrototypeBean { }
```

### 싱글톤 빈에서 프로토 타입빈을 사용 주의점
> 싱글톤 빈에서 프로토타입 스코프 빈을 사용시에 이 빈도 싱글톤처럼 동작된다.

#### 1. ObjectProvider, ObjectFactory 를 사용하여 처리할 수 있다. (스프링 의존성)

```java
@Scope("singleton")
@RequiredArgsConstructor
static class SingletonBean {
    private final ObjectProvider<PrototypeBean> provider;
    public int logic() {
        PrototypeBean prototypeBean = provider.getObject();
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
```

#### 2. javax.inject.Provider 라는 JSR-330 자바 표준을 사용
> jakarta.inject.Provider 를 사용해서 거의 동일하게 구현할 수 있다.   
> 단 gradle에 jakarta.inject:jakarta.inject-api:2.0.1 를 추가 해야 된다.

```java
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
```

### request scope
> request scope 의 경우에도 지연 로딩을 해야 되고, 이를 주입하는 방법에는 ObjectProvider, Proxy 형태로 처리할 수 있다.

#### ObjectProvider 사용 
```java
@Component
@Slf4j
@Scope(value = "request")
public class MyLogger {
    private String uuid;
    private String requestURL;
}

@Controller
@RequiredArgsConstructor
public class LogDemoController {
    private final ObjectProvider<MyLogger> myLoggerProvider;
}
```

#### proxy 로 처리
```java
@Component
@Slf4j
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {
    private String uuid;
    private String requestURL;
}
```