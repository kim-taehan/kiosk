package org.devleopx.study.spring.fatory_bean;

import org.springframework.beans.factory.FactoryBean;

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
