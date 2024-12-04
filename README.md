# Task Manager 
## Description
The **Task Manager API** is a backend system built with **Spring Boot** to help users manage tasks efficiently. It includes features like task creation, prioritization, setting due dates, and tracking the status of tasks. Users can also create and manage subtasks under tasks. The system is secured with **JWT Authentication**.

---

## Features
- **User Authentication**:
  - **JWT-based authentication** for login and signup.
- **Task Management**:
  - **Create, update, delete**, and **retrieve tasks**.
  - Assign **priority** and **due date** to tasks.
  - **Soft delete** functionality for tasks.
- **Subtask Management**:
  - Create and manage **subtasks** under tasks.
- **Secure Endpoints**:
  - Role-based access control using **Spring Security** and **JWT**.
  

---

## Technologies Used
- **Backend Framework**: Spring Boot
- **Database**: MySQL
- **ORM**: Hibernate/JPA
- **Security**: Spring Security with JWT
- **API Documentation**: Swagger UI
- **Build Tool**: Maven

---

## Getting Started

### Prerequisites
- **Java 17** or later
- **MySQL** database
- **Maven**

### Setting Up the Project

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/ramanjeet-code/taskManage.git