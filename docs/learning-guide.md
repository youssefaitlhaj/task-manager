# Java Advanced Learning Guide
## Task Manager Project — From Intermediate to Advanced Java

---

## Table of Contents

1. [Project Overview](#1-project-overview)
2. [Tech Stack](#2-tech-stack)
3. [Development Environment](#3-development-environment)
4. [Project Structure](#4-project-structure)
5. [SBT Build Tool](#5-sbt-build-tool)
6. [Git & Version Control](#6-git--version-control)
7. [Java Packages](#7-java-packages)
8. [Java 8 vs Java 21](#8-java-8-vs-java-21)
9. [Domain Model](#9-domain-model)

---

## 1. Project Overview

We are building a **Task Manager application** — a mini version of Jira.

### Features
- User registration and login with roles (Admin, Member)
- Create and manage Projects
- Create Tasks inside projects with priority, status, due date, and assignee
- Add comments to tasks
- Attach tags to tasks

### Two entry points
| Client | Technology | Description |
|--------|-----------|-------------|
| REST API | Tapir + Scala | Consumed by the desktop client and testable via Postman |
| Desktop UI | Java Swing | A desktop application that calls the REST API |

---

## 2. Tech Stack

| Layer | Technology | Why |
|-------|-----------|-----|
| Build tool | SBT | Scala's standard build tool, supports mixed Java/Scala projects |
| Backend language | Java 21 | Domain model, business logic, database, UI |
| API language | Scala | Tapir is a Scala-native library |
| REST framework | Tapir | Type-safe endpoint definition |
| Database | PostgreSQL | Free, open source, industry standard |
| DB access | jOOQ | Type-safe SQL queries, free tier supports PostgreSQL |
| Migrations | Flyway | Version-controlled database schema changes |
| Unit tests | JUnit 5 | Standard Java testing framework |
| Spec tests | ScalaTest | Behavior-driven tests for Scala code |
| Integration tests | Testcontainers | Spins up a real PostgreSQL in Docker for tests |
| Desktop UI | Java Swing | Java-native UI framework |
| CI/CD | Jenkins | Automated build, test, and release pipeline |

### Why mix Java and Scala?

Both languages run on the **JVM (Java Virtual Machine)** and compile to the same bytecode. They can call each other freely. This is common in enterprise projects.

- Java handles the **domain, business logic, database, and UI** — core Java skills
- Scala handles the **REST API** — because Tapir is idiomatic Scala

---

## 3. Development Environment

### What is a Dev Container?

A Dev Container is a **Docker container** configured as a development environment. Instead of installing Java, SBT, PostgreSQL, and other tools directly on your laptop, everything runs inside an isolated container.

**Your laptop only needs:**
- Docker Desktop — runs the container
- VS Code or IntelliJ — connects to the container

**Inside the container:**
- Java 21
- Scala + SBT
- PostgreSQL (as a separate sidecar container)
- All project dependencies

**Why use a Dev Container?**
- Your laptop stays clean — no global installations
- The environment is reproducible — same setup for every developer
- Easy to reset — delete the container and start fresh

### Dev Container configuration

The configuration lives in `.devcontainer/` at the root of the project:

```
.devcontainer/
├── devcontainer.json    ← tells VS Code which container to use
└── docker-compose.yml   ← defines the app container and PostgreSQL
```

**docker-compose.yml** defines two services:
- `app` — the development container with Java and SBT
- `postgres` — the PostgreSQL database container

---

## 4. Project Structure

The project is split into **modules**. Each module has a single responsibility. This is called **Separation of Concerns**.

```
task-manager/
├── domain/         ← Java: core entities (Task, User, Project)
├── application/    ← Java: business logic and services
├── infrastructure/ ← Java: database access (jOOQ + Flyway)
├── api/            ← Scala: Tapir REST endpoints
├── ui/             ← Java: Swing desktop client
└── build.sbt       ← SBT build configuration
```

### Why split into modules?

| Benefit | Explanation |
|---------|-------------|
| **Single responsibility** | Each module does one thing only |
| **Independent testing** | Test each module in isolation |
| **Clear dependencies** | You know exactly what depends on what |
| **Easier to navigate** | You always know where to find things |

### Module dependencies

```
domain        → no dependencies (pure core, knows nothing else)
application   → depends on domain
infrastructure→ depends on domain
api           → depends on application
ui            → depends on application
```

This means: `application` can use classes from `domain`, but `domain` knows nothing about `application`. This keeps the core clean and independent.

---

## 5. SBT Build Tool

SBT (Simple Build Tool) is the standard build tool for Scala projects. It also supports Java. It is the equivalent of Maven or Gradle.

### build.sbt

The `build.sbt` file is the heart of an SBT project. It is written in Scala.

```scala
ThisBuild / scalaVersion := "3.4.0"       // Scala version for all modules
ThisBuild / version      := "0.1.0-SNAPSHOT" // application version
ThisBuild / organization := "com.taskmanager" // project identifier

lazy val domain = (project in file("domain"))
  .settings(name := "domain")

lazy val application = (project in file("application"))
  .settings(name := "application")
  .dependsOn(domain)  // application can use domain classes

lazy val root = (project in file("."))
  .aggregate(domain, application, infrastructure, api, ui)
  .settings(name := "task-manager")
```

### Key keywords

| Keyword | Purpose |
|---------|---------|
| `.dependsOn(x)` | This module can use code from module `x` |
| `.aggregate(...)` | Running `sbt compile` on root compiles all listed modules |
| `file(".")` | The current directory (where `build.sbt` lives) |

### Running SBT

SBT must always be run from the directory where `build.sbt` lives.

```bash
cd task-manager
sbt compile    # compiles all modules
sbt test       # runs all tests
```

### Comparison: SBT vs Maven

```scala
// SBT — build.sbt (Scala code, concise)
ThisBuild / organization := "com.taskmanager"
ThisBuild / version      := "0.1.0-SNAPSHOT"
```

```xml
<!-- Maven — pom.xml (XML, verbose) -->
<project>
  <groupId>com.taskmanager</groupId>
  <artifactId>task-manager</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</project>
```

---

## 6. Git & Version Control

### .gitignore

The `.gitignore` file tells Git which files and folders to never track.

**Rule of thumb:** if a file is generated or personal to your machine, it belongs in `.gitignore`.

```
# SBT generated folders — recreated on every compile
target/
project/target/
project/project/

# IDE personal settings
.idea/       ← IntelliJ
.metals/     ← Scala language server
.vscode/     ← VS Code

# macOS system file — useless to everyone else
.DS_Store
```

### GitFlow Branching Strategy

GitFlow is the industry standard for managing code in a team project.

| Branch | Purpose |
|--------|---------|
| `main` | Production-ready code only. Never commit directly here. |
| `develop` | Integration branch. All features merge here first. |
| `feature/xxx` | One branch per feature. Created from `develop`. |
| `release/xxx` | Preparing a release. Created from `develop`. |
| `hotfix/xxx` | Emergency fix on production. Created from `main`. |

### The flow

```
main
 └── develop
      ├── feature/user-management
      ├── feature/task-crud
      └── feature/project-management
```

**Every new piece of work = a new feature branch.**

```bash
# Start a new feature
git checkout develop
git checkout -b feature/task-crud

# Work, commit, push
git add .
git commit -m "Add task CRUD endpoints"
git push -u origin feature/task-crud

# When done, merge back to develop via Pull Request
```

---

## 7. Java Packages

In Java, the **folder structure must mirror the package name**. This is a strict compiler rule.

### Package naming convention

Package names follow your domain name reversed:

```
taskmanager.com  →  com.taskmanager
```

This convention guarantees global uniqueness and prevents class name collisions with third-party libraries.

### Folder structure

| Package declaration | Required folder path |
|---------------------|---------------------|
| `com.taskmanager.domain` | `src/main/java/com/taskmanager/domain/` |
| `com.taskmanager.application` | `src/main/java/com/taskmanager/application/` |
| `com.taskmanager.api` | `src/main/scala/com/taskmanager/api/` |

### Example

```java
// File location: domain/src/main/java/com/taskmanager/domain/Task.java
package com.taskmanager.domain;   // must match folder path

public record Task(...) {}
```

If the package declaration and the folder path do not match, the compiler will refuse to compile.

---

## 8. Java 8 vs Java 21

### Enums

Both Java 8 and Java 21 support enums. The syntax is identical.

```java
// Java 8 and Java 21 — same syntax
public enum TaskStatus {
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    IN_REVIEW("In Review"),
    DONE("Done");

    private final String label;

    TaskStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
```

**When to use enums:** when all variants have the same shape (same fields).

### Sealed Interfaces (Java 21 only)

Sealed interfaces allow you to define a **closed set of implementations** where each variant can have a different shape.

```java
// Java 21 only
public sealed interface Shape
    permits Shape.Circle, Shape.Rectangle {

    record Circle(double radius)         implements Shape {}
    record Rectangle(double w, double h) implements Shape {}
}
```

`Circle` has only `radius`. `Rectangle` has `width` and `height`. They are completely independent.

The `permits` keyword means **only these listed classes** can implement this interface. The compiler blocks anything else.

**When to use sealed interfaces:** when variants have different data or different behavior.

| | Enum | Sealed Interface |
|---|---|---|
| Fixed set of values | ✅ | ✅ |
| All variants same shape | ✅ | ✅ |
| Different data per variant | ❌ | ✅ |
| Pattern matching (switch) | Limited | ✅ Full |

### Records (Java 21)

A Record is a concise way to create an **immutable data class**. The compiler automatically generates the constructor, getters, `equals()`, `hashCode()`, and `toString()`.

```java
// Java 8 — 30+ lines of boilerplate
public final class User {
    private final Long id;
    private final String username;
    private final String email;

    public User(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }

    @Override public boolean equals(Object o) { ... }
    @Override public int hashCode() { ... }
    @Override public String toString() { ... }
}

// Java 21 — 1 line
public record User(Long id, String username, String email) {}
```

**Records are immutable** — all fields are `final` by default. You cannot change a field after the record is created. This is perfect for domain entities that represent a snapshot of data.

**Accessing fields in a record:**
```java
User user = new User(1L, "john", "john@example.com");
user.id()       // returns 1L   (no "get" prefix)
user.username() // returns "john"
user.email()    // returns "john@example.com"
```

### Primitive vs Object types

| Primitive | Object | Difference |
|-----------|--------|-----------|
| `long` | `Long` | Object can be `null`, primitive cannot |
| `int` | `Integer` | Object can be `null`, primitive cannot |
| `boolean` | `Boolean` | Object can be `null`, primitive cannot |

**In domain entities, always use object types (`Long`, `Integer`)** because database values can be `null` — for example, an `id` is `null` before the record is saved to the database.

### Date and Time types

| Type | Use for | Example |
|------|---------|---------|
| `LocalDate` | A date with no time | Due date: `2024-02-01` |
| `LocalDateTime` | A date with exact time | Created at: `2024-01-15 14:32:05` |
| `LocalTime` | A time with no date | Opening hours: `09:00` |

---

## 9. Domain Model

The domain module contains the **core entities** of the application. These are pure Java classes with no dependencies on any framework or library.

```
domain/src/main/java/com/taskmanager/domain/
├── TaskStatus.java   ← enum: TODO, IN_PROGRESS, IN_REVIEW, DONE
├── Priority.java     ← enum: LOW, MEDIUM, HIGH, CRITICAL
├── User.java         ← record: id, username, email, role
├── Project.java      ← record: id, name, description, ownerId
└── Task.java         ← record: id, title, description, status, priority, ...
```

### Why use IDs instead of full objects?

Domain entities reference each other by **ID**, not by embedding the full object.

```java
// Wrong — Task embeds the full User object
public record Task(Long id, String title, User assignee, ...) {}

// Correct — Task references User by ID only
public record Task(Long id, String title, Long assigneeId, ...) {}
```

**Reasons:**
- Entities stay lightweight — no risk of loading the entire database
- Decoupled from each other — changing `User` does not break `Task`
- Matches how databases work — foreign keys are IDs, not full rows
