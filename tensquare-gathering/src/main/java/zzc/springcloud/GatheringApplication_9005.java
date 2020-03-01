package zzc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import zzc.springcloud.util.IdWorker;


@SpringBootApplication
@EnableCaching
public class GatheringApplication_9005 {

	public static void main(String[] args) {
		SpringApplication.run(GatheringApplication_9005.class, args);
	}

	@Bean
	public IdWorker idWorkker() {
		return new IdWorker(1, 1);
	}

}
