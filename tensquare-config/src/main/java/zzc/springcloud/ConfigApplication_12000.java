package zzc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer // 开启配置服务
public class ConfigApplication_12000 {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ConfigApplication_12000.class, args);
	}

}
