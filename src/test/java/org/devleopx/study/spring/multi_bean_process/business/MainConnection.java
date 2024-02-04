package org.devleopx.study.spring.multi_bean_process.business;

public class MainConnection implements Connection {
    @Override
    public String getConnection() {
        return "main connection";
    }
}
