package org.devleopx.study.spring.multi_bean_process.business;

public class SubConnection implements Connection {
    @Override
    public String getConnection() {
        return "sub connection";
    }
}
