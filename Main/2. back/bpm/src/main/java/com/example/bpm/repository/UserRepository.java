package com.example.bpm.repository;

import com.example.bpm.entity.UserEntity;
import com.example.bpm.entity.UserPKEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, UserPKEntity> {

}
