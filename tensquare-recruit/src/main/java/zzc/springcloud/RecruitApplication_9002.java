package zzc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import zzc.springcloud.util.IdWorker;

@SpringBootApplication
public class RecruitApplication_9002 {

	public static void main(String[] args) {
		SpringApplication.run(RecruitApplication_9002.class, args);
	}

	@Bean
	public IdWorker idWorkker() {
		return new IdWorker(1, 1);
	}

}
