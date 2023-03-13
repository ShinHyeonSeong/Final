package com.example.testdemo.entity;


import com.example.testdemo.dto.AccountDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "account")
public class AccountEntity {
    @Id
    @Column(name = "accountid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountid;
    @Column(name = "email")
    private String email;
    @Column(name = "nickname")
    private String nickname;
    @OneToMany(mappedBy = "id1")
    private List<FriendEntity> friendEntityList1 = new ArrayList<>();

    @OneToMany(mappedBy = "id2")
    private List<FriendEntity> friendEntityList2 = new ArrayList<>();

    public static AccountEntity toAccountEntity(AccountDto accountDto) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountid(accountDto.getAccountid());
        accountEntity.setEmail(accountDto.getEmail());
        accountEntity.setNickname(accountDto.getNickname());
        return accountEntity;
    }

    public static AccountEntity toUpdateaccountEntity(AccountDto accountDto) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountid(accountDto.getAccountid());
        accountEntity.setEmail(accountDto.getEmail());
        accountEntity.setNickname(accountDto.getNickname());
        return accountEntity;
    }
}