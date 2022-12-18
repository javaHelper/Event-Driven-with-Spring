package com.bridgingcode;

import com.bridgingcode.binding.ProductOrdersBindings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(ProductOrdersBindings.class)
public class ProductOrdersAnalyticsKafkaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductOrdersAnalyticsKafkaDemoApplication.class, args);
	}

}