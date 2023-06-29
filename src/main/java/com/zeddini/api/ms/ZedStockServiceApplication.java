package com.zeddini.api.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient         // To enable eureka client
@EnableFeignClients // To enable Open Feign client
public class ZedStockServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZedStockServiceApplication.class, args);
	}

}
