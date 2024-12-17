

# Project Setup and Architecture // 4/12/2024

This project is built using **Spring Boot 3.4.0** with **Java 23** and **Maven**. Below is a step-by-step guide on how the project was structured and the configuration applied.

## Steps Followed:

1. **Initial Project Setup:**
   - Used [Spring Initializr](https://start.spring.io/) to create the Spring Boot project.
   - Chose **Maven** as the build tool.
   - Selected **Java 23** as the version.
   - Included necessary dependencies for the project (e.g., Spring Web, Spring Data JPA, etc.).

2. **Project Structure:**
   The project is organized into two main packages:
   
   - **`business layer`:** Responsible for handling the controller and service classes. 
     - **Controller:** Handles HTTP requests and responses.
     - **Service:** Contains business logic for processing data.

   - **`data layer`:** Responsible for data management and persistence. 
     - **Repository:** Interfaces for database access.
     - **Models:** Define the structure of the data used in the application.
## Technologies Used:

- **Java 23**
- **Spring Boot 3.4.0**
- **Maven**
- **Spring Web**
- **Spring Data JPA**




## Updates 

 - **`business layer`:
     - **Controller:**
          attendController
          ProfileManagementController

     - **Service:**
          attendService
          profileManagment

   - **`data layer`:
     - **Repository:**.
          added function findByLessonId to LessonAttendanceRepository


     - **Models:** 



