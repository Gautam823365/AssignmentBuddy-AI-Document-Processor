package com.example.assignment_buddy_pdf_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AssignmentBuddyPdfServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentBuddyPdfServiceApplication.class, args);
	}

}
