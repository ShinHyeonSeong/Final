package com.example.bpm.repository;

import com.example.bpm.entity.FriendRequestEntity;
import com.example.bpm.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequestEntity, String> {
}
