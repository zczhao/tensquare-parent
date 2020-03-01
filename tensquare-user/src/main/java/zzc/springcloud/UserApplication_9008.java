package zzc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import zzc.springcloud.util.IdWorker;
import zzc.springcloud.util.JwtUtil;

@SpringBootApplication
@EnableEurekaClient
public class UserApplication_9008 {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication_9008.class, args);
	}

	@Bean
	public IdWorker idWorkker() {
		return new IdWorker(1, 1);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtUtil jwtUtil() {
		return new JwtUtil();
	}
}
