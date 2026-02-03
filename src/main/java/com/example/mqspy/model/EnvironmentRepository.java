package com.example.mqspy.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EnvironmentRepository extends CrudRepository<Environment, Long> {
    @Query("select name from Environment")
    Optional<List<String>> findAllNames();
}
