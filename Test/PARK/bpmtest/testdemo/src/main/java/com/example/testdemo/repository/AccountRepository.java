package com.example.testdemo.repository;

import com.example.testdemo.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
//    @Query("SELECT fl.id, fl.id2.accountId, fl.id2.nickName FROM FriendEntity fl WHERE fl.id1.accountId=:id1")
//    public abstract List<List<String>> findId2ById1AccountId(String id1);

    Optional<AccountEntity> findByEmail(String email);
}
