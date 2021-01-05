package cn.iszhangheng.oauth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 无权限处理类
 */
@Configuration
public class TinyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        //返回json形式的错误信息
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        String error = "{\"code\":4441,\"msg\":\"无权访问\"}";
        httpServletResponse.getWriter().println(error);
        httpServletResponse.getWriter().flush();
    }
}
