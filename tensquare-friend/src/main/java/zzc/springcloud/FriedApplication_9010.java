package zzc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import zzc.springcloud.util.JwtUtil;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class FriedApplication_9010 {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(FriedApplication_9010.class, args);
	}

	@Bean
	public JwtUtil jwtUtil() {
		return new JwtUtil();
	}
}
