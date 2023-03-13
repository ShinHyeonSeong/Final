package com.example.testdemo.service;

import com.example.testdemo.dto.AccountDto;
import com.example.testdemo.entity.AccountEntity;
import com.example.testdemo.entity.FriendEntity;
import com.example.testdemo.repository.AccountRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    final private AccountRepository accountRepository;

    public void save(AccountDto accountDto) {
        //DTO -> Entity 변환
        AccountEntity accountEntity = AccountEntity.toAccountEntity(accountDto);
        //Repository의 내장 함수 save로 DB 넘김
        accountRepository.save(accountEntity);
    }

    public AccountDto login(AccountDto accountdto) {
        //DB에 이메일 조회
        Optional<AccountEntity> byUserEmail = accountRepository.findByEmail(accountdto.getEmail());
        //조회한 이메일 DB에 존재하는가?
        if (byUserEmail.isPresent()) {
            //Optional로 묶여진 byUserEmail 값을 Entity로 변환
            AccountEntity accountEntity = byUserEmail.get();
            //Entity -> DTO 변환
            AccountDto result = accountdto.toAccountDto(accountEntity);
            return result;
        } else {
            return null;
        }
    }

        //사용자 목록 가져오기
        public List<AccountDto> findAll () {
            List<AccountEntity> accountEntityList = accountRepository.findAll();
            List<AccountDto> accountdtoList = new ArrayList<>();
            for (AccountEntity accountEntity : accountEntityList) {
//            accountdto accountdto = accountdto.toaccountdto(FriendEntity);
//            accountdtoList.add(accountdto);               밑에 함수와 같은 결과
                accountdtoList.add(AccountDto.toAccountDto(accountEntity));
            }
            return accountdtoList;
        }

        public AccountDto findById (Long id){
            Optional<AccountEntity> optionalAccountEntity = accountRepository.findById(id);
            if (optionalAccountEntity.isPresent()) {
                //Optional로 묶여져 있는 객체 중 하나의 정보만 가지고 와야 하므로 get() 메소드 이용
                return AccountDto.toAccountDto(optionalAccountEntity.get());
            } else {
                return null;
            }
        }

        public AccountDto findById1 (String email){
            Optional<AccountEntity> optionalAccountEntity = accountRepository.findByEmail(email);
            if (optionalAccountEntity.isPresent()) {
                return AccountDto.toAccountDto(optionalAccountEntity.get());
            } else {
                return null;
            }
        }

        public AccountDto updateForm (String myEmail){
            Optional<AccountEntity> optionalAccountEntity = accountRepository.findByEmail(myEmail);
            if (optionalAccountEntity.isPresent()) {
                return AccountDto.toAccountDto(optionalAccountEntity.get());
            } else {
                return null;
            }
        }

        public void update (AccountDto accountdto){

            accountRepository.save(AccountEntity.toUpdateaccountEntity(accountdto));
        }

        public void deleteById (Long id){
            accountRepository.deleteById(id);
        }
    }