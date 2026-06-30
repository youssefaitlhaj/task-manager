package com.taskmanager.application;

import com.taskmanager.domain.Priority;
import com.taskmanager.domain.Task;
import com.taskmanager.domain.TaskStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// What is does
public interface TaskService {

    Task createTask(String title, String description, Priority priority, Long projectId, Long assigneeId, LocalDate dueDate);

    Task updateTaskStatus(Long taskId, TaskStatus status);

    Task assignTask(Long taskId, Long assigneeId);

    Optional<Task> getTaskById(Long taskId);

    List<Task> getTasksByProject(Long projectId);
}
