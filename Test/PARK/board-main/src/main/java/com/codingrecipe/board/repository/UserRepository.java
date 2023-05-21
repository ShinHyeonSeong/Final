package com.codingrecipe.board.repository;

import com.codingrecipe.board.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;
import java.util.Optional;
@Repository
@Table(name = "user_info")
public interface UserRepository extends JpaRepository<UserEntity, String> {
    @Query(value = "select * from user_info where email = :email", nativeQuery = true)
    Optional<UserEntity> findByEmail(String email);

    public List<UserEntity> findAllByEmailContaining(String searchKeyword);
}
