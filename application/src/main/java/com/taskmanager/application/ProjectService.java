package com.taskmanager.application;

import com.taskmanager.domain.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Project createProject(String name, String description, Long ownerId);

    Optional<Project> getProjectById(Long projectId);

    List<Project> getProjectsByUser(Long userId);
}
