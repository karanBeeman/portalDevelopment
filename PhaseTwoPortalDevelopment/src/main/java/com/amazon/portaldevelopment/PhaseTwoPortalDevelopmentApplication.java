package com.amazon.portaldevelopment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.function.client.WebClient;

import com.amazon.portaldevelopment.configuration.ConfigurationPropertiesClass;

//@SpringBootApplication
//@EnableAutoConfiguration
//@ComponentScan(basePackages = "com.amazon.*")
//@EnableJpaRepositories(basePackages="com.amazon.Repository")
@SpringBootApplication(exclude= { DataSourceAutoConfiguration.class })
//@EntityScan(basePackages="com.amazon.portaldevelopment.entity")

public class PhaseTwoPortalDevelopmentApplication {
	
	@Bean
	public WebClient.Builder getWebClientInstance(){
		return WebClient.builder();
	}
	
	@Bean
	public ConfigurationPropertiesClass configuration() {
		return new ConfigurationPropertiesClass();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(PhaseTwoPortalDevelopmentApplication.class, args);
	}

}