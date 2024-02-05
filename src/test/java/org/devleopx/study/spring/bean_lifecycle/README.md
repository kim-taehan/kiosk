## 빈 생명주기 콜백 시작
> 데이터베이스 커넥션 풀이나, 네트워크 소켓처럼 애플리케이션 시작 시점에 필요한 연결을 미리 해두고,
애플리케이션 종료 시점에 연결을 모두 종료하는 작업을 진행하려면, 객체의 초기화와 종료 작업이
필요하다

### 스프링은 크게 3가지 방법으로 빈 생명주기 콜백을 지원한다.
#### 1. 인터페이스(InitializingBean, DisposableBean)
```java
@Slf4j
static class Client implements InitializingBean, DisposableBean {

    private String welcomeWord;

    @Override
    public void destroy() throws Exception {
        log.info("goodbye LifeCycleInterface.class");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.welcomeWord = "hello";
    }
}
```

#### 2. 설정 정보에 초기화 메서드, 종료 메서드 지정
```java
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
    // destroyMethod (close, shutdown) 으로 추론함)
    @Bean(destroyMethod = "close", initMethod = "init")
    public Client client() {
        return new Client();
    }
}
```
#### 3. @PostConstruct, @PreDestroy 애노테이션 지원

```java
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
```

### 결론
> 자바 표준으로 제공하는 @PostConstruct, @PreDestroy 애노테이션 방식을 사용하고   
> 외부 라이브러리 처럼 소스 수정이 어려운 경우 Bean 설정으로 구현하자.