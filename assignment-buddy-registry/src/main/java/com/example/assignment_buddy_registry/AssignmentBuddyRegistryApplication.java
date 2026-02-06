package com.example.assignment_buddy_registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class AssignmentBuddyRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentBuddyRegistryApplication.class, args);
	}

}
