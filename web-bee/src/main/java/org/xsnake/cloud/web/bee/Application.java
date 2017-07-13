package org.xsnake.cloud.web.bee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.xsnake.cloud.web.bee.interceptor.RightInterceptor;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan(basePackages="org.xsnake")
public class Application extends WebMvcConfigurerAdapter{
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/assets/**")
	            .addResourceLocations("classpath:/assets/");
	}
	
	  @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor(new RightInterceptor()).addPathPatterns("/**");
	        super.addInterceptors(registry);
	    }
}
