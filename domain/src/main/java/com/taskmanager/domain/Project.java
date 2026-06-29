package com.taskmanager.domain;

public record Project(Long id, String name, String description, Long ownerId) {
}
