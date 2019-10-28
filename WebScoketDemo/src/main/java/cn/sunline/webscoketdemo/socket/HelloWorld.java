package cn.sunline.webscoketdemo.socket;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class HelloWorld {
    @RequestMapping(value = "/hello")
    public String helloWorld(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        String result = "HELLO WORLD!";
        for (String items : params.keySet()) {
            result += params.get(items);
        }
        System.out.println("接受到请求!");
        return result;
    }
}
