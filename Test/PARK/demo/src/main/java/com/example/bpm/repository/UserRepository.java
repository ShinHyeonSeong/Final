package com.example.bpm.repository;

import com.example.bpm.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
//    Optional<UserEntity> findByUseridAndPassword(String userid, String password);
    Optional<UserEntity> findByEmail(String email);


}
