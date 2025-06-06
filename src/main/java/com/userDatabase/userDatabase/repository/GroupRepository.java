package com.userDatabase.userDatabase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.userDatabase.userDatabase.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByNameContaining(String name);
}