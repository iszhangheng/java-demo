package cn.iszhangheng.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TinyResourceConfig extends ResourceServerConfigurerAdapter {


    @Resource
    private Environment environment;

    @Autowired
    private RemoteTokenServices remoteTokenServices;

    @Autowired
    private cn.sunline.tiny.security.TinyAccessDeniedHandler tinyAccessDeniedHandler;
    @Autowired
    private cn.sunline.tiny.security.TinyAuthenticationEntryPoint tinyAuthenticationEntryPoint;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        List<String> stringList = new ArrayList<String>() {
            {
                add("/resources/**");
                add("/axure/**");
                add("/tiny_app_me.tml");
                add("/tiny_app_table.tml");
                add("/tiny_app_add_app.tml");
                add("/tiny_app_del_app.tml");
                add("/tiny_app_store_list.tml");
                add("/tiny_mall_app_list.tml");
                add("/tiny_app_first_startup.tml");
                add("/tiny_app_check_version.tml");
                add("/tiny_tbs_add_page_marketing.tml");
                add("/tiny_tbs_page_marketing_list.tml");
                add("/tiny_tbs_super_app_list.tml");
                add("/login.tml");
                add("/service_info.tml");
                add("/tiny_tbs_pages.tml");
                add("/tiny_app_store_list1.tml");
                add("/**");
            }
        };
        if ("k8s".equals(environment.getProperty("spring.profiles.active"))) {
            stringList.add("/**");
        }

        // 基于token，所以不需要session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.antMatcher("/**").authorizeRequests()
                .and()
                .authorizeRequests()
                .antMatchers(stringList.toArray(new String[0])).permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling()
                //添加自定义异常
                .accessDeniedHandler(tinyAccessDeniedHandler)
                .authenticationEntryPoint(tinyAuthenticationEntryPoint);
    }

    /**
     * 当token为redis方式时需要配置
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(remoteTokenServices)
                //添加自定义异常
                .accessDeniedHandler(tinyAccessDeniedHandler)
                .authenticationEntryPoint(tinyAuthenticationEntryPoint);
    }
}
