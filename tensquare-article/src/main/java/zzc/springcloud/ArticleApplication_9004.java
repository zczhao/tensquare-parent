package zzc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import zzc.springcloud.util.IdWorker;

@SpringBootApplication
@EnableEurekaClient
public class ArticleApplication_9004 {

	public static void main(String[] args) {
		SpringApplication.run(ArticleApplication_9004.class, args);
	}

	@Bean
	public IdWorker idWorkker() {
		return new IdWorker(1, 1);
	}

}
