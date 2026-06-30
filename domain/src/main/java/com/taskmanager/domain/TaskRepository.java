package com.taskmanager.domain;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Optional<Task> findById(Long taskId);
    List<Task> findByAssigneeId(Long assigneeId);
    List<Task> findByProjectId(Long projectId);
    List<Task> findByPriority(Priority priority);
    List<Task> findByStatus(TaskStatus status);
    List<Task> findAll();
    
    Task save(Task task);
    
    void deleteById(Long taskId);
}
