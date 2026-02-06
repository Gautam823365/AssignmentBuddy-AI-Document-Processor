package com.example.assignment_buddy_user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AssignmentBuddyUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentBuddyUserServiceApplication.class, args);
	}

}
