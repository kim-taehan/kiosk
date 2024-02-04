package org.devleopx.study.spring.multi_bean_process.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
public class Client {

    private final Connection connection;

    public String run() {
        return connection.getConnection();
    }
}
