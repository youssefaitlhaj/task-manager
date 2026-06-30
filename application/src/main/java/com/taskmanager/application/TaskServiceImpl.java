package com.taskmanager.application;


import com.taskmanager.domain.Priority;
import com.taskmanager.domain.Task;
import com.taskmanager.domain.TaskStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// How it does it
public class TaskServiceImpl implements TaskService {
    
    @Override
    public Task createTask(String title, String description, Priority priority, Long projectId, Long assigneeId, LocalDate dueDate) {
        return null;
    }

    @Override
    public Task updateTaskStatus(Long taskId, TaskStatus status) {
        return null;
    }

    @Override
    public Task assignTask(Long taskId, Long assigneeId) {
        return null;
    }

    @Override
    public Optional<Task> getTaskById(Long taskId) {
        return Optional.empty();
    }

    @Override
    public List<Task> getTasksByProject(Long projectId) {
        return List.of();
    }
}
