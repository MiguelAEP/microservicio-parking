package com.vehiculo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VehiculoRegistreApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehiculoRegistreApplication.class, args);
	}

}
