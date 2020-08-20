package com.example.component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Receiver {

    public void receiveMessage(String message) {
        log.info("监听者1收到消息：{}", message);
    }
}
