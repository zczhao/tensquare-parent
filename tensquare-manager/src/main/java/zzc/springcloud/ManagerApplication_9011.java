package zzc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import zzc.springcloud.util.JwtUtil;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ManagerApplication_9011 {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ManagerApplication_9011.class, args);
	}
	
	@Bean
	public JwtUtil jwtUtil() {
		return new JwtUtil();
	}
}
