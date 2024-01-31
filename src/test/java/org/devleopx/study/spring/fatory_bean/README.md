# FactoryBean 사용
```text
스프링에서 DI할 수 없는 클래스의 빈이 있을 수 있다. 예를 들어 클래스 정보를 미리 알아낼 수 없는 경우이다
 1. Dynamic Proxy로 만드는 객체의 경우
 2. 정적 팩토리 클래스로 접근해야 하는 객체처럼 new 연산자로 생성할 수 없는 객체
 
이를 위해 스프링에서는 FactoryBean이라는 어댑터 클래스를 제공한다.
```

```java
public interface FactoryBean {
    T getObject() throws Exception;
    Class<?> getObjectType();
    boolean isSingleton();
}
```

### 빈으로 사용하고 싶은 클래스
-  정적 팩토리 클래스로 접근해야 하는 객체라서 스프링이 DI할 수 없다.
 
```java
@RequiredArgsConstructor(access = PRIVATE)
public class Message {

    private final String text;

    // 정적 팩토리 클래스로 접근해야 하는 객체처럼 new 연산자로 생성할 수 없는 객체
    public static Message newMessage(String text) {
        return new Message(text);
    }
}
```


### 클래스를 생성하는 역할을 하는 MessageFactoryBean을 정의

```java
public class MessageFactoryBean implements FactoryBean<Message> {

    @Override
    public Message getObject() throws Exception {
        return Message.newMessage("hello spring");
    }

    @Override
    public Class<?> getObjectType() {
        return Message.class;
    }
}
```