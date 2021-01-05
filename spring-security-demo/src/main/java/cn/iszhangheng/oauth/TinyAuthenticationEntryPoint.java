package cn.iszhangheng.oauth;

import cn.sunline.tiny.util.ResultUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token失效处理类
 */
@Configuration
public class TinyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //返回json形式的错误信息
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        String error = JSONObject.toJSONString(ResultUtil.error("pleaseLogin"));
        httpServletResponse.getWriter().println(error);
        httpServletResponse.getWriter().flush();
    }
}
