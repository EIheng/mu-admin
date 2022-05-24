package com.mudongheng.app.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashSet;

/**
 * @author 幕冬
 * @since 2022年01月12日
 */
@Slf4j
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    // 注册Sa-Token的注解拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("启用sa-token注解拦截器");
        // 注册注解拦截器，并排除不需要注解鉴权的接口地址 (与登录拦截器无关)
        registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**");
    }

    /**
     * 注册 [Sa-Token全局过滤器]
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        HashSet<String> set = new HashSet<>();
        set.add("http://admin.mudongheng.com");
        set.add("https://admin.mudongheng.com");
        set.add("http://localhost:8080/");

        return new SaServletFilter()
                // 拦截与排除 path
                .addInclude("/**")
                // 异常处理函数
                .setError(Throwable::getMessage)
                // 前置函数：在每次认证函数之前执行
                .setBeforeAuth(obj -> {
                    // ---------- 设置跨域响应头 ----------
                    // 跨域处理
                    String origin = SaHolder.getRequest().getHeader("Origin");
                    SaHolder.getResponse()
                            .setHeader("Access-Control-Allow-Origin", set.contains(origin) ? origin : "http://localhost:8080")
                            // 允许所有请求方式
                            .setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE")
                            // 有效时间
                            .setHeader("Access-Control-Max-Age", "3600")
                            // 允许的header参数
                            .setHeader("Access-Control-Allow-Headers", "*");
                    // 如果是预检请求，则立即返回到前端
                    SaRouter.match(SaHttpMethod.OPTIONS)
                            .back();
                });
    }

}