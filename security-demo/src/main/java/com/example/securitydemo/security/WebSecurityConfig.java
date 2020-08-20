package com.example.securitydemo.security;

import cn.sunline.tiny.security.Authentication.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Description: 由于security是由UsernamePasswordAuthenticationFilter这个类定义登录的,
 * 里面默认是/login路径,我们要让他用我们的/mylogin路径,就需要配置.loginProcessingUrl("/mylogin")
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 配置了自定义登入登出Handler，优先响应登入登出Handler， 这里是返回json给前端处理，后端重定向设置不起效果
     */
    private static final Logger LOG = LoggerFactory.getLogger(cn.sunline.tiny.security.WebSecurityConfig.class);
    @Resource
    MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Resource
    MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Resource
    MyLogoutSuccessHandle myLogoutSuccessHandle;
    @Resource
    MyAccessDeniedHandler myAccessDeniedHandler;
    @Resource
    MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    @Resource
    MyUserDetailsService myUserDetailsService;
    @Resource
    private DataSource dataSource;
    @Resource
    LindTokenAuthenticationFilter lindTokenAuthenticationFilter;

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //增加自定义的UserDetailService
        userDetailsAuthenticationProvider.setUserDetailsService(userDetailsService);
        //设置一个Provider
        auth.authenticationProvider(userDetailsAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        免登录路径
        String[] strings = new String[]{
                "/login.tml",
                "/**"
        };
        // 将我们自定义的验证码过滤器，配置到UsernamePasswordAuthenticationFilter之前
        http.authorizeRequests()
                .antMatchers(strings).permitAll()// 定义不需要认证就可以访问
                .anyRequest().authenticated()// 其余所有请求都需要登录认证才能访问
                .and()
                .formLogin()
                // 指定自定义form表单请求的路径
                .loginProcessingUrl("/login").usernameParameter("userName").passwordParameter("passWord")
                // .defaultSuccessUrl("/success")
                .successForwardUrl("/success")// 设置了登入登出的Handler,优先响应Handler
                .failureUrl("/fail")// 设置了登入登出的Handler,优先响应Handler
                // 自定义认证成功或者失败的返回json
                .successHandler(myAuthenticationSuccessHandler).failureHandler(myAuthenticationFailureHandler)
                // 这个formLogin().permitAll()方法允许所有用户基于表单登录访问/login这个page。
                .permitAll()
                .and()
                // 自定义退出url
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/login").logoutSuccessHandler(myLogoutSuccessHandle)// 设置了登入登出的Handler,优先响应Handler
                .invalidateHttpSession(true).permitAll().and().rememberMe()// 记住我
                .rememberMeParameter("rememberMe").tokenRepository(persistentTokenRepository())
                .userDetailsService(myUserDetailsService).tokenValiditySeconds(30 * 60);
        // 默认都会产生一个hiden标签 里面有安全相关的验证 防止请求伪造 这边我们暂时不需要 可禁用掉
        http.csrf().disable();
        http.exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint);// 未登录
        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler); // 无权访问
        //加入过滤器
        http.addFilterBefore(lindTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

    /**
     * 密码加密的bean
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    }

}