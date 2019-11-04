package cn.sunline.webscoketdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


@SpringBootApplication
public class WebscoketdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebscoketdemoApplication.class, args);
    }

}
