# 📝 Task Tracker Backend

A simple Spring Boot REST API to manage task lists and tasks.

---

## 🚀 Tech Stack

- Java 21  
- Spring Boot 3.5.3  
- Spring Data JPA  
- PostgreSQL  
- Maven  

---

## 📂 Features

- ✅ CRUD operations for Task Lists  
- ✅ CRUD operations for Tasks (nested under Task Lists)  

---

## 📄 API Endpoints

### 🔹 Task Lists

- `GET /task-lists` — Get all task lists  
- `POST /task-lists` — Create a new task list  
- `GET /task-lists/{id}` — Get a specific task list by ID  
- `PUT /task-lists/{id}` — Update a task list by ID  
- `DELETE /task-lists/{id}` — Delete a task list by ID  

### 🔸 Tasks

- `GET /task-lists/{taskListId}/tasks` — Get all tasks in a task list  
- `POST /task-lists/{taskListId}/tasks` — Create a new task in a task list  
- `GET /task-lists/{taskListId}/tasks/{taskId}` — Get a specific task by ID within a task list  
- `PUT /task-lists/{taskListId}/tasks/{taskId}` — Update a task by ID within a task list  
- `DELETE /task-lists/{taskListId}/tasks/{taskId}` — Delete a task by ID within a task list  

---

## ⚙️ Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/OneOmar/task-tracker-backend.git
cd task-tracker-backend

### 2. Configure the database

Edit the `src/main/resources/application.properties` file with your PostgreSQL credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tasks_db
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password

### 3. Run the application

Run the following command:

```bash
./mvnw spring-boot:run

## ✅ Todo

- [ ] Add Swagger/OpenAPI documentation  
- [ ] Dockerize the application  
- [ ] Implement authentication (JWT)

