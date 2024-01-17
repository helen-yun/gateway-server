package com.pongift.marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.pongift.common", "com.pongift.marketplace"})
public class MarketplaceApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}
}
