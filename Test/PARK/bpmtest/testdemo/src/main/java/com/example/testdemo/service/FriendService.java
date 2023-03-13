package com.example.testdemo.service;

import com.example.testdemo.dto.AccountDto;
import com.example.testdemo.dto.FriendDto;
import com.example.testdemo.entity.AccountEntity;
import com.example.testdemo.entity.FriendEntity;
import com.example.testdemo.repository.AccountRepository;
import com.example.testdemo.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    final private AccountRepository accountRepository;


    public List<FriendDto> findAll(Long accountid) {
        List<FriendEntity> friendEntityList = friendRepository.findById1(accountid);
        List<FriendDto> friendDtoList = new ArrayList<>();
        for (FriendEntity friendEntity : friendEntityList) {
            friendDtoList.add(FriendDto.toFriendDto(friendEntity));
        }
        return friendDtoList;
    }

    public AccountDto search(AccountDto accountDto) {
        Optional<AccountEntity> searchEntity = accountRepository.findById(accountDto.getAccountid());
        if (searchEntity.isPresent()) {
            AccountDto searchDto = new AccountDto();
            return (searchDto.toAccountDto(searchEntity.get()));
        } else {
            log.info("찾으신 친구 검색 결과가 없습니다.");
            return null;
        }
    }

    public AccountDto requestFriend(AccountDto accountDto) {

    }

    public void request() {

    }

}