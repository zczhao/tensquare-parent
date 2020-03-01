package zzc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import zzc.springcloud.util.IdWorker;

@SpringBootApplication
@EnableEurekaClient
public class BaseApplication_9001 {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BaseApplication_9001.class, args);
	}

	@Bean
	public IdWorker idWorker() {
		return new IdWorker(1, 1);
	}
}
