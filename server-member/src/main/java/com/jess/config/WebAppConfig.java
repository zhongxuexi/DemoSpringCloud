package com.jess.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 自定义静态资源映射
 * Created by zhongxuexi on 2018/7/22.
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
    @Value("${web.upload-path}")
    private String path;
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error_page/401.html");
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error_page/404.html");
                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
                container.addErrorPages(error401Page, error404Page, error500Page);
            }
        };
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**").addResourceLocations("file:"+path);
        super.addResourceHandlers(registry);
    }
}
