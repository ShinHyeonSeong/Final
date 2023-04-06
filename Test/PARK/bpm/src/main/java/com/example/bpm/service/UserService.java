package com.example.bpm.service;

import com.example.bpm.dto.UserDto;
import com.example.bpm.entity.UserEntity;
import com.example.bpm.entity.UserPKEntity;
import com.example.bpm.repository.UserRepository;
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
public class UserService {
    private final UserRepository userRepository;

    //유저 정보 저장 (회원가입)
    public void save(UserDto userDto) {
        UserEntity userEntity = UserEntity.toUserEntity(userDto);
        userRepository.save(userEntity);
    }

    //유저 로그인
    public UserDto login(UserDto userDto) {
        UserPKEntity userPKEntity = new UserPKEntity(userDto.getUuid(), userDto.getEmail());
        Optional<UserEntity> loginUserInfo = userRepository.findById(userPKEntity);
        if (loginUserInfo.isPresent()) {
            log.info("아이디가 없습니다");
            UserEntity userEntity = loginUserInfo.get();
            if (userEntity.getPassword().equals(userDto.getPassword())) {
                log.info("비밀번호가 일치하지 않습니다.");
                UserDto result = userDto.toUserDto(userEntity);
                return result;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    //사용자 목록 가져오기
    public List<UserDto> findAll() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (UserEntity userEntity : userEntityList) {
            userDtoList.add(UserDto.toUserDto(userEntity));
        }
        return userDtoList;
    }

    //사용자 ID 찾기
    public UserDto findById(UserDto userDto) {
        UserPKEntity userPKEntity = new UserPKEntity(userDto.getUuid(), userDto.getEmail());
        Optional<UserEntity> loginUserInfo = userRepository.findById(userPKEntity);
        if (loginUserInfo.isPresent()) {
            return UserDto.toUserDto(loginUserInfo.get());
        } else {
            return null;
        }
    }


    //업데이트를 위한 현재 정보 가져오기
    public UserDto updateForm(UserDto userDto) {
        UserPKEntity userPKEntity = new UserPKEntity(userDto.getUuid(), userDto.getEmail());
        Optional<UserEntity> loginUserInfo = userRepository.findById(userPKEntity);
        if (loginUserInfo.isPresent()) {
            return UserDto.toUserDto(loginUserInfo.get());
        } else {
            return null;
        }
    }



    //유저 탈퇴
    public void deleteById(UserDto userDto) {
        UserPKEntity userPKEntity = new UserPKEntity(userDto.getUuid(), userDto.getEmail());
        userRepository.deleteById(userPKEntity);
    }


}