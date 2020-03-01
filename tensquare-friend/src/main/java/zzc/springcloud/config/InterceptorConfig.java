package zzc.springcloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import zzc.springcloud.interceptor.JWTInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport{

	@Autowired
	private JWTInterceptor jwtInterceptor;
	
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		// 注册拦截器要申明拦载器和要拦截的请求
		registry.addInterceptor(jwtInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns("/**/login/**");
	}
	
}
