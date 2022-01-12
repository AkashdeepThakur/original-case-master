package com.klm.cases.df;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.klm.cases.df.interceptor.ApiMonitor;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/index.html").addResourceLocations("classpath:/static/index.html");
    }
    
    /* registering below interceptor to intercept call for custom metrics */
    
    /*@Autowired
    ApiMonitor interceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
     // this interceptor will be applied to all URLs
     registry.addInterceptor(interceptor);
    } 
    */

}
