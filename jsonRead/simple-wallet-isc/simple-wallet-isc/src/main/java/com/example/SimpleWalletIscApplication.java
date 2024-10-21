package com.example;

import com.example.model.CsvProcessor;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication(scanBasePackages = {"com.example"})
@EnableJpaRepositories("com.example.repository")
@EntityScan(basePackages =  "com.example.model")
@ComponentScan(basePackages = "com.example.model")
public class SimpleWalletIscApplication {

	private static final Logger log = LoggerFactory.getLogger(SimpleWalletIscApplication.class);

	private final CsvProcessor csvProcessor;

	@Autowired
	public SimpleWalletIscApplication(CsvProcessor csvProcessor) {
		this.csvProcessor = csvProcessor;
	}


	public static void main(String[] args) {
		SpringApplication.run(SimpleWalletIscApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@PostConstruct
	public void run() {
		// Define the paths to your customer and account CSV files
		String customerFilePath ="src/main/resources/cutomers.csv"; // Update this path
		String accountFilePath = "src/main/resources/accounts.csv";   // Update this path

		try {
			// Process both CSV files
			csvProcessor.processCustomerCsv(customerFilePath);
			csvProcessor.processAccountCsv(accountFilePath);
			log.info("CSV processing completed successfully.");
		} catch (Exception e) {
			log.error("Unexpected error: ", e);
		}
	}
}