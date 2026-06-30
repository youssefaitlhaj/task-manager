package com.taskmanager.application;

import com.taskmanager.domain.Project;

import java.util.List;
import java.util.Optional;

public class ProjectServiceImpl implements ProjectService {
    @Override
    public Project createProject(String name, String description, Long ownerId) {
        return null;
    }

    @Override
    public Optional<Project> getProjectById(Long projectId) {
        return Optional.empty();
    }

    @Override
    public List<Project> getProjectsByUser(Long userId) {
        return List.of();
    }
}
