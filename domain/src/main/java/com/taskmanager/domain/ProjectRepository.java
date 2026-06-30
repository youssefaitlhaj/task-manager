package com.taskmanager.domain;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    Project save(Project project);

    Optional<Project> findById(Long projectId);

    List<Project> findByOwnerId(Long ownerId);

    List<Project> findAll();

    void deleteById(Long projectId);
}
