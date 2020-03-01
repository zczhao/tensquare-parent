package zzc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import zzc.springcloud.util.IdWorker;

@SpringBootApplication
public class SpitApplication_9006 {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpitApplication_9006.class, args);
	}

	@Bean
	public IdWorker idWorkker() {
		return new IdWorker();
	}
}
