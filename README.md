
# Project Overview and API Documentation

This project is built using **Spring Boot 3.4.0**, **Java 23**, and **Maven**. Below is a detailed breakdown of the system functionality and API endpoints to serve as a comprehensive guide for developers.

---

## Table of Contents

- [Project Setup and Architecture](#project-setup-and-architecture)
- [API Endpoints](#api-endpoints)
  - [User Management](#user-management)
  - [Course Management](#course-management)
  - [Lesson Management](#lesson-management)
  - [Enrollment](#enrollment)
  - [Attendance](#attendance)
  - [Profile Management](#profile-management)
  - [Notifications](#notifications)
  - [Assessment Management](#assessment-management)
- [Technologies Used](#technologies-used)
- [Updates](#updates)

---

## Project Setup and Architecture

### Steps Followed

1. **Initial Project Setup**
   - Used [Spring Initializr](https://start.spring.io/) to generate the Spring Boot project.
   - Selected **Maven** as the build tool and **Java 23** as the language version.
   - Included dependencies like Spring Web, Spring Data JPA, and others as per project requirements.

2. **Project Structure**
   - **Business Layer**
     - **Controller:** Handles HTTP requests and maps them to appropriate service logic.
     - **Service:** Contains business logic for data processing.
   - **Data Layer**
     - **Repository:** Interfaces for data persistence and database access.
     - **Models:** Defines the structure of data entities.

---

## API Endpoints

### User Management

#### Admin
```json
POST /admin/create
{
    "name": "arwa",
    "email": "arwaaa222@example.com",
    "userType": "ADMIN",
    "password": "securePass123",
    "gender": "female",
    "birthDate": "2000-03-01",
    "entityType": "ADMIN",
    "signUpKey": "c9f5b4e2-2d1f-47fa-9b98-62a0b7c4d6f2"
}
```

#### Student
```json
POST /student/create
{
    "name": "stude1",
    "email": "stude2333@example.com",
    "userType": "STUDENT",
    "password": "securePass123",
    "gender": "female",
    "birthDate": "2000-03-01",
    "entityType": "STUDENT",
    "gpa": 3.56
}
```

#### Instructor
```json
POST /instructor/create
{
    "name": "marwa",
    "email": "marwa@example.com",
    "userType": "INSTRUCTOR",
    "password": "securePass123",
    "gender": "female",
    "birthDate": "2000-03-01",
    "entityType": "INSTRUCTOR"
}
```

### Course Management

#### Create Course
```json
POST /course/create/{instructorId}
{
    "name": "Arabic",
    "maxNumberOfStudent": 50,
    "description": "......",
    "instructor": {
        "id": 1,
        "entityType": "INSTRUCTOR"
    },
    "studentCourses": [],
    "lessons": []
}
```

#### Update Course
```json
PUT /course/update
{
    "id": 1,
    "description": "A beginner's course in Java programming language."
}
```

#### Delete Course
```http
DELETE /course/delete/{courseId}
```

#### View All Courses
```http
GET /course/view
```

### Lesson Management

#### Add Lesson to Course
```json
POST /course/addLesson
{
    "otp": 123456,
    "duration": 90,
    "contentFile": "base64encodedcontent",
    "course": {
        "id": 8
    }
}
```

### Enrollment

#### Enroll in a Course
```http
POST /enroll/{courseId}/{studentId}
```

#### View Enrolled Students
```http
GET /enroll/view/{courseId}
```

#### Drop a Course
```http
DELETE /drop/{courseId}/{studentId}
```

### Attendance

#### Mark Attendance
```http
GET /attend?courseId=1&lessonId=1&studentId=3&studentOtp=101112
```

#### View Attendance
```http
GET /attend/course/{courseId}/lesson/{lessonId}
```

### Profile Management

#### View Profile
```http
GET /profile/{id}
```

#### Update Profile
```json
PUT /profile/updateStudent/{id}
{
    "entityType": "STUDENT"
}
```

### Notifications

#### Get Notifications
- All Notifications:
```http
GET /{userId}/Notifications/all
```
- Unread Notifications:
```http
GET /{userId}/Notifications/unread
```

#### Send Notifications
- To All Enrolled Students:
```json
POST /{courseId}/CreateNotifications/allEnrolled
{
    "notificationContent": "We have a quiz tomorrow."
}
```
- To Specific Students:
```json
POST /{courseId}/CreateNotifications/SpecificStudents
{
    "notificationContent": "Quiz Retake tomorrow",
    "students": [1, 2, 3, 4]
}
```

### Assessment Management

#### Create Assessment
```json
POST /{courseId}/createAssessment
{
    "totalGrades": 10,
    "duration": 2.0,
    "startDate": "2024-12-30",
    "type": "assignment",
    "questions": [6],
    "random": false,
    "totalNumberOfQuestions": 1
}
```

#### Display Assessment
```http
GET /{userId}/{courseId}/{assessmentId}/displayAssessment
```

#### Submit Assessment
- Quiz:
```json
POST /{userId}/{courseId}/{assessmentId}/displayAssessment/submitQuiz
{
    "q1": "a",
    "q2": "b",
    "q3": true
}
```
- Assignment:
Submit as Form-Data with file content.

---

## Technologies Used

- **Java 23**
- **Spring Boot 3.4.0**
- **Maven**
- **Spring Web**
- **Spring Data JPA**
- **MySQL**



---
