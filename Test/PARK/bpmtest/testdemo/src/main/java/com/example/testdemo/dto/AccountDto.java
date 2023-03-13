package com.example.testdemo.dto;

import com.example.testdemo.entity.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long accountid;
    private String email;
    private String nickname;

    public static AccountDto toAccountDto(AccountEntity accountEntity) {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountid(accountEntity.getAccountid());
        accountDto.setEmail(accountEntity.getEmail());
        accountDto.setNickname(accountEntity.getNickname());
        return accountDto;
    }

}