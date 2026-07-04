package com.taskmanager.application;

import com.taskmanager.domain.Project;
import com.taskmanager.domain.ProjectRepository;

import java.util.List;
import java.util.Optional;

public class ProjectServiceImpl implements ProjectService {
    
    private final ProjectRepository projectRepository;
    
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    
    @Override
    public Project createProject(String name, String description, Long ownerId) {
        Project project = new Project(null, name, description, ownerId);
        return projectRepository.save(project);
    }

    @Override
    public Optional<Project> getProjectById(Long projectId) {
        return projectRepository.findById(projectId);
    }

    @Override
    public List<Project> getProjectsByOwnerId(Long ownerId) {
        return projectRepository.findByOwnerId(ownerId);
    }
}
