package cn.sunline.webscoketdemo.socket;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 1.主要用于标记配置类，兼备Component的效果
 * 2.开启定时任务
 *
 * @author sunline
 * @date 2016/10/31
 */
@Component
@Configuration
@EnableScheduling
public class WebSocketTimer {
    @Scheduled(cron = "0/5 * * * * ?")
    private void configureTasks() {
        MyWebSocket.sendMessageToAll("当前在线人数：" + MyWebSocket.getSessionSize());
    }
}