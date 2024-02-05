## 조회 대상 빈이 2개 이상일 때 해결 방법
> 하나의 인터페이스를 2개의 이상의 빈으로 등록되었을 경우가 있다.  
> 이런 경우에는 처리방법에 대해서 정리한다. 

### 예제 상황 
> 코드에서 자주 사용하는 메인 데이터베이스의 커넥션을 획득하는 스프링 빈이 있고,    
> 코드에서 특별한 기능으로 가끔 사용하는 서브 데이터베이스의 커넥션을 획득하는 스프링 빈이 있다고 생각해보자.

```java
// connection interface
public interface Connection {
    String getConnection();
}

// main connection
public class MainConnection implements Connection {
    @Override
    public String getConnection() {
        return "main connection";
    }
}

// sub connection
public class SubConnection implements Connection {
    @Override
    public String getConnection() {
        return "sub connection";
    }
}

// client 호출
@RequiredArgsConstructor
public class Client {

    private final Connection connection;

    public void run() {
        System.out.println(connection.getConnection());
    }
}
```

## 해결방법
> 아래의 4가지 해결방법을 조합해서 처리하자. 조합하여 사용할 수도 있으며,
> 지금은 수동 주입 방식으로 처리했지만 자동 컴포넌트 주입에서도 사용할 수 있다. 


### 1. 빈 이름으로 매칭
> 타입 매칭의 결과가 2개 이상일 때 필드 명, 파라미터 명으로 빈 이름 매칭
```java
@Configuration
static class App {
    @Bean
    public Connection mainConnection() {
        return new MainConnection();
    }
    @Bean
    public Connection subConnection() {
        return new SubConnection();
    }
    // 타입 매칭의 결과가 2개 이상일 때 필드 명, 파라미터 명으로 빈 이름 매칭
    @Bean
    public Client client(Connection mainConnection) {
        return new Client(mainConnection);
    }
}
```

### @Primary 사용
> 2개의 빈이 등록되는 경우 @Primary로 우선순위를 정할 수 있다.
```java
@Configuration
static class App {
    @Bean
    @Primary
    public Connection mainConnection() {
        return new MainConnection();
    }
    @Bean
    public Connection subConnection() {
        return new SubConnection();
    }
    @Bean
    public Client client(Connection connection) {
        return new Client(connection);
    }
}
```

### @Qualifier 사용
> 2개의 빈이 등록되는 경우 @Qualifier로 직접 매칭을 할 수 있다.
```java
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
```

### @Primary 는 기본값으로 @Qualifier 는 Annotation 해서 사용하기
```java
public class SubConnection implements Connection {
    @Override
    public String getConnection() {
        return "sub connection";
    }
}

@Configuration
static class App {
    @Bean
    @Primary
    public Connection mainConnection() {
        return new MainConnection();
    }
    @Bean
    @SubAnnotation
    public Connection subConnection() {
        return new SubConnection();
    }
    @Bean
    public Client client(@SubAnnotation Connection connection) {
        return new Client(connection);
    }
}
```

