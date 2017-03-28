package com.example.repositories;

import com.example.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findOne(long Id);

    UserEntity findByUuid(String uuid);

    UserEntity findByName(String name);

    List<UserEntity> findByNameIgnoreCaseContaining(String name);

    // UserEntity findByDescription(String description);

    // Iterable<UserEntity> findEntity(String name);
}