package org.devleopx.study.spring.fatory_bean;

import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class Message {

    private final String text;

    // 정적 팩토리 클래스로 접근해야 하는 객체처럼 new 연산자로 생성할 수 없는 객체
    public static Message newMessage(String text) {
        return new Message(text);
    }

    public String getText() {
        return text;
    }
}
