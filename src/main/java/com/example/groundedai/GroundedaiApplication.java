package com.example.groundedai;

import com.example.groundedai.common.config.AiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AiProperties.class)
public class GroundedaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroundedaiApplication.class, args);
	}

}
