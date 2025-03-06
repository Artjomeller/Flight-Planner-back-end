# Flight Planner Backend

This is the backend service for the Flight Planner application. This README provides instructions on how to set up and run the backend project.

## Prerequisites

- Java Development Kit (JDK) 17 or later
- Maven
- IntelliJ IDEA

## Project Setup

### Clone the repository from GitHub
Clone the repository to your local machine:
    git clone <repository-url>
    cd flight-planner

## Open the project in IntelliJ IDEA
1. Launch IntelliJ IDEA.
2. Select "Open" or "Import Project".
3. Navigate to the cloned project folder and select it.
4. Wait for IntelliJ to import the project and download dependencies.

## Running the Application

### Application Structure

The application has a simple structure with the main class already set up:


package com.example.flightplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlightPlannerApplication {
public static void main(String[] args) {
SpringApplication.run(FlightPlannerApplication.class, args);
}
}

## Run the Application
To run the application:

1. Find the FlightPlannerApplication.java file in the project explorer at src/main/java/com/example/flightplanner/FlightPlannerApplication.java.
2. Right-click on the file and select "Run FlightPlannerApplication".
3. Wait for the application to start. You should see Spring Boot logs in the console.

## Verifying the Application is Running
Open a web browser and go to:
http://localhost:8080
You should see a confirmation that the backend service is running.

## Technologies
Java 17 or later
Spring Boot
Maven

### © 2025 Artjom Eller