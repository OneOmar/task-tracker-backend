# 📘 Task Tracker API Documentation

This REST API provides CRUD operations for managing **Task Lists** and their associated **Tasks**.

---

## 📂 Task Lists Endpoints

### 🔹 Get all task lists
**GET** `/task-lists`  
Returns a list of all task lists.

**Response Body:**

```json
[
  {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "title": "Work Projects",
    "description": "Tasks related to ongoing work projects.",
    "count": 5,
    "progress": 0.4,
    "tasks": []
  },
  {
    "id": "789a1234-b56c-78d9-e012-123456789012",
    "title": "Personal Errands",
    "description": "Daily and weekly personal tasks.",
    "count": 3,
    "progress": 0.75,
    "tasks": []
  }
]
```

### 🔹 Create a new task list
**POST** `/task-lists`  
Creates a new task list.

**Request Body:**

```json
{
  "title": "New Task List",
  "description": "A new list for upcoming tasks."
}
```

**Response Body:**

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "title": "New Task List",
  "description": "A new list for upcoming tasks."
}
```

### 🔹 Get task list details
**GET** `/task-lists/{taskListId}`  
Returns the details of a specific task list, including all its tasks.

**Response Body:**

```json
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "title": "Work Projects",
  "description": "Tasks related to ongoing work projects.",
  "count": 5,
  "progress": 0.4,
  "tasks": [
    {
      "id": "a1b2c3d4-e5f6-7890-1234-567890123456",
      "title": "Finish API documentation",
      "description": "Review and update the API spec.",
      "dueDate": "2025-08-10T10:00:00Z",
      "priority": "HIGH",
      "status": "OPEN"
    },
    {
      "id": "b2c3d4e5-f6a7-8901-2345-678901234567",
      "title": "Deploy to production",
      "description": "Release new feature to live environment.",
      "dueDate": "2025-08-15T14:30:00Z",
      "priority": "HIGH",
      "status": "CLOSED"
    }
  ]
}
```

### 🔹 Update an existing task list
**PUT** `/task-lists/{taskListId}`  
Updates an existing task list.

**Request Body:**

```json
{
  "title": "Updated Work Projects",
  "description": "Updated description for work tasks."
}
```

**Response Body:**

```json
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "title": "Updated Work Projects",
  "description": "Updated description for work tasks."
}
```

### 🔹 Delete a task list
**DELETE** `/task-lists/{taskListId}`  
Deletes a task list and all its associated tasks.

**Response:** 204 No Content

## 🧩 Tasks Endpoints

### 🔹 Get all tasks for a task list
**GET** `/task-lists/{taskListId}/tasks`  
Returns a list of all tasks for a specific task list.

**Response Body:**

```json
[
  {
    "id": "a1b2c3d4-e5f6-7890-1234-567890123456",
    "title": "Finish API documentation",
    "description": "Review and update the API spec.",
    "dueDate": "2025-08-10T10:00:00Z",
    "priority": "HIGH",
    "status": "OPEN"
  },
  {
    "id": "b2c3d4e5-f6a7-8901-2345-678901234567",
    "title": "Deploy to production",
    "description": "Release new feature to live environment.",
    "dueDate": "2025-08-15T14:30:00Z",
    "priority": "HIGH",
    "status": "CLOSED"
  }
]
```

### 🔹 Create a new task
**POST** `/task-lists/{taskListId}/tasks`  
Creates a new task within a specific task list.

**Request Body:**

```json
{
  "title": "Write new blog post",
  "description": "Draft an article on API best practices.",
  "dueDate": "2025-08-20T09:00:00Z",
  "priority": "MEDIUM",
  "status": "OPEN"
}
```

**Response Body:**

```json
{
  "id": "c3d4e5f6-a7b8-9012-3456-789012345678",
  "title": "Write new blog post",
  "description": "Draft an article on API best practices.",
  "dueDate": "2025-08-20T09:00:00Z",
  "priority": "MEDIUM",
  "status": "OPEN"
}
```

### 🔹 Get a specific task
**GET** `/task-lists/{taskListId}/tasks/{taskId}`  
Returns a specific task by its ID.

**Response Body:**

```json
{
  "id": "a1b2c3d4-e5f6-7890-1234-567890123456",
  "title": "Finish API documentation",
  "description": "Review and update the API spec.",
  "dueDate": "2025-08-10T10:00:00Z",
  "priority": "HIGH",
  "status": "OPEN"
}
```

### 🔹 Update an existing task
**PUT** `/task-lists/{taskListId}/tasks/{taskId}`  
Updates an existing task.

**Request Body:**

```json
{
  "title": "Finalize API spec",
  "description": "Ensure all endpoints are properly documented.",
  "dueDate": "2025-08-10T17:00:00Z",
  "priority": "HIGH",
  "status": "CLOSED"
}
```

**Response Body:**

```json
{
  "id": "a1b2c3d4-e5f6-7890-1234-567890123456",
  "title": "Finalize API spec",
  "description": "Ensure all endpoints are properly documented.",
  "dueDate": "2025-08-10T17:00:00Z",
  "priority": "HIGH",
  "status": "CLOSED"
}
```

### 🔹 Delete a specific task
**DELETE** `/task-lists/{taskListId}/tasks/{taskId}`  
Deletes a specific task.

**Response:** 204 No Content

## ❗ Error Handling

The API returns standard HTTP status codes for success and failure cases.

| Code | Meaning                |
|------|------------------------|
| 200  | OK                     |
| 201  | Created                |
| 204  | No Content             |
| 400  | Bad Request            |
| 404  | Not Found              |
| 500  | Internal Server Error  |

## 🧪 Technologies Used

- Java 21
- Spring Boot 3.5.3
- PostgreSQL
- Maven
- Docker

## 🚀 Getting Started

Clone the repository.

Run the Docker containers:

```bash
docker-compose up
```

Access the API at: `http://localhost:8080`

© 2025 Omar EL MANSSOURI. All rights reserved.