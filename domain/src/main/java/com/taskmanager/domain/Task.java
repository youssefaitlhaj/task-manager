package com.taskmanager.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Task(Long id, String title, String description, TaskStatus status, Priority priority, Long projectId,
                   Long assigneeId, LocalDateTime createdAt, LocalDate dueDate) {
}