package com.example.bpm.repository;

import com.example.bpm.entity.UserEntity;
import com.example.bpm.entity.UserPKEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, UserPKEntity> {
    Optional<UserEntity> deleteById(String Id);

    Optional<UserEntity> findById(String Id);


}
