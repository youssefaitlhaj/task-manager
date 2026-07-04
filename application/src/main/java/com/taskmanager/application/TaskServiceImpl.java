package com.taskmanager.application;


import com.taskmanager.domain.Priority;
import com.taskmanager.domain.Task;
import com.taskmanager.domain.TaskRepository;
import com.taskmanager.domain.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// How it does it
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    @Override
    public Task createTask(String title, String description, Priority priority, Long projectId, Long assigneeId, LocalDate dueDate) {
        Task task = new Task(null, title, description, TaskStatus.TODO, priority, projectId, assigneeId, LocalDateTime.now(), dueDate);
        return taskRepository.save(task);
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
        return taskRepository.findById(taskId);
    }

    @Override
    public List<Task> getTasksByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }
}
