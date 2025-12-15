# TaskTracker – Design Outline

## 1. Project Overview

TaskTracker is a multi-user task management application built to demonstrate backend engineering skills using Java, Spring Boot, Maven, and MySQL. The system allows users to create tasks, optionally assign due dates, and share tasks with other users while enforcing clear ownership and authorization rules.

The project is intentionally designed as a backend-first, production-style service, emphasizing clean architecture, security, data modeling, and maintainability rather than UI complexity.

---

## 2. Goals and Non-Goals

### Goals

* Provide a secure, multi-user task tracking system
* Enforce task ownership and sharing rules
* Demonstrate realistic backend architecture and practices
* Maintain a clean, extensible domain model

### Non-Goals (Initial Version)

* Subtasks (deferred)
* Notifications or reminders
* Real-time updates
* UI-heavy frontend (API-first design)

---

## 3. Core Domain Concepts

### User

Represents an authenticated system user.

Key characteristics:

* Each user has a **user-facing username** and a **hidden internal userId**
* Users authenticate using a username and password
* Passwords are stored as secure hashes (never plaintext)

Key fields:

* userId (UUID, primary key, internal)
* username (unique, user-facing)
* passwordHash
* createdAt
* updatedAt

---

### Task

Represents a unit of work created by a user.

Key characteristics:

* Each task has a single creator (owner)
* Tasks may be shared with other users for visibility
* Only the creator may modify or delete a task
* Tasks may optionally have a due date

Key fields:

* taskId (UUID, primary key)
* title
* description
* status (OPEN, IN_PROGRESS, FINISHED)
* dueDate (nullable)
* ownerId (references User.userId)
* createdAt
* updatedAt

---

### Task Sharing

Tasks can be shared with multiple users.

Rules:

* Shared users may view the task
* Shared users may not modify or delete the task
* Sharing does not transfer ownership

Implementation approach:

* Many-to-many relationship via a join table (e.g., task_users)

---

## 4. Business Rules

* Usernames must be unique
* Passwords must be securely hashed
* Only authenticated users may access the system
* A task has exactly one owner
* Only the task owner may:

    * Update task details
    * Change task status
    * Delete the task
* Shared users may only view tasks
* Task status must be one of:

    * OPEN
    * IN_PROGRESS
    * FINISHED
* Due dates are optional

---

## 5. Authentication and Authorization (High-Level)

* Authentication is based on username and password
* Successful authentication resolves to an internal userId
* Authorization decisions are enforced in the service layer
* Task modification operations validate task ownership using userId comparison

---

## 6. Architecture Overview

The application follows a layered architecture:

* **Controller Layer**: REST endpoints and request validation
* **Service Layer**: Business logic, authorization checks, and orchestration of repository operations
* **Repository Layer**: Persistence using Spring Data JPA
* **Domain Layer (Model Layer)**: Contains the core business entities and value objects (e.g., User, Task, TaskStatus), representing system state and intrinsic business rules

The application exposes a RESTful API and uses JSON for data exchange. The frontend can consume these APIs, allowing both backend and frontend skills to be demonstrated.

---

## 7. Data Persistence

* MySQL is used as the relational database
* UUIDs are used as primary keys
* Foreign key constraints are enforced
* Schema evolution is managed via database migrations

---

## 8. Deferred Enhancements

Potential future improvements based on the current design:

1. **Subtasks** – Implement a self-referencing hierarchy for tasks.
2. **Role-based permissions** – Extend beyond creator-only modification rules if needed.

These features are explicitly out of scope for the initial implementation but are supported by the current domain design.

---

## 9. Status

This document reflects the initial design and will evolve alongside implementation decisions.
