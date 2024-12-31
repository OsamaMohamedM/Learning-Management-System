# Project Overview and API Documentation

This project is built using **Spring Boot 3.4.0**, **Java 23**, and **Maven**. Below is a detailed breakdown of the system functionality and API endpoints to serve as a comprehensive guide for developers.

---

## Table of Contents

- [Project Setup and Architecture](#project-setup-and-architecture)
- [Technologies Used](#technologies-used)
- [Key Components](#key-components)
- [API Endpoints](#api-endpoints)
  - [User Management](#user-management)
  - [Course Management](#course-management)
  - [Lesson Management](#lesson-management)
  - [Enrollment](#enrollment)
  - [Attendance](#attendance)
  - [Profile Management](#profile-management)
  - [Notifications](#notifications)
  - [Assessment Management](#assessment-management)
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


## Technologies Used

- **Java 23**
- **Spring Boot 3.4.0**
- **Maven**
- **Spring Web**
- **Spring Data JPA**
- **MySQL**
- **Security: Spring Security for role-based access control**

---

## Key Components

### 1. User Management

- **User Types:** Admin, Instructor, Student.
- **Admin:** Manages overall system settings, creates users, and manages courses.
- **Instructor:** Creates courses, manages course content, adds assignments and quizzes, grades students, and removes students from courses.
- **Student:** Enrolls in courses, accesses course materials, takes quizzes, hands in assignments, and views grades.

**Features:**
- User Registration and Login (role-based access).
- Profile Management (view/update profile information).

### 2. Course Management

- **Course Creation:**
  - Instructors can create courses with details like title, description, and duration.
  - Instructors can upload media files (videos, PDFs, audio, etc.).
  - Course consists of a number of lessons to be attended by students.

- **Enrollment Management:**
  - Students can view available courses and enroll.
  - Admins and Instructors can view the list of enrolled students per course.

- **Attendance Management:**
  - Instructors can generate an OTP per lesson to maintain attendance.
  - Students can select the lesson and enter the OTP received from the instructor.

### 3. Assessment & Grading

- **Assessment Types:** Quiz, Assignment.
- **Quiz Creation:**
  - Instructors can create quizzes with different question types (MCQ, true/false, short answers).
  - Instructors can maintain a question bank per course.
  - Randomized question selection for each quiz attempt.

- **Assignment Submission:**
  - Students can upload files for review by Instructors.

- **Grading and Feedback:**
  - Instructors can grade assignments.
  - Students receive automated feedback after quizzes and manual feedback on assignments.

### 4. Performance Tracking

- **Student Progress Tracking:**
  - Instructors can track quiz scores, assignment submissions, and attendance.

### 5. Notifications

- **System Notifications:**
  - Notifications for enrollment confirmation, graded assignments, and course updates.
  - Students can filter notifications as unread or view all.
  - Instructors receive notifications about student enrollments.


- **Role-Based Access Control:**
  - Using Spring Security for authentication and authorization.
  - Restricts access permissions based on user roles.

- **Email Notifications:**
  - Students receive email updates for enrollment confirmations, graded assignments, and course updates.

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

