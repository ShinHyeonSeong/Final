package com.example.bpm.service;

import com.example.bpm.dto.UserDto;
import com.example.bpm.entity.UserEntity;
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
    final private UserRepository userRepository;

    //유저 정보 저장 (회원가입)
    public void save(UserDto userDto) {
        //DTO -> Entity 변환
        UserEntity userEntity = UserEntity.toUserEntity(userDto);
        //Repository의 내장 함수 save로 DB 넘김
        userRepository.save(userEntity);
    }

    //유저 로그인
    public UserDto login(UserDto userDto) {
        //DB에 이메일 조회
        Optional<UserEntity> byUserEmail = userRepository.findByUserEmail(userDto.getEmail());
        //조회한 이메일 DB에 존재하는가?
        if (byUserEmail.isPresent()) {
            //Optional로 묶여진 byUserEmail 값을 Entity로 변환
            UserEntity userEntity = byUserEmail.get();
            if (userEntity.getPassword().equals(userDto.getPassword())) {
                //Entity -> DTO 변환
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
            UserDto userDto = UserDto.toUserDto(userEntity);
//            userDtoList.add(userDto);               밑에 함수와 같은 결과
            userDtoList.add(UserDto.toUserDto(userEntity));
        }
        return userDtoList;
    }

    //사용자 ID 찾기
    public UserDto findById(String userEmail) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserEmail(userEmail);
        if (optionalUserEntity.isPresent()) {
            //Optional로 묶여져 있는 객체 중 하나의 정보만 가지고 와야 하므로 get() 메소드 이용
            return UserDto.toUserDto(optionalUserEntity.get());
        } else {
            return null;
        }
    }


    //업데이트를 위한 현재 정보 가져오기
    public UserDto updateForm(String myEmail) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserEmail(myEmail);
        if (optionalUserEntity.isPresent()) {
            return UserDto.toUserDto(optionalUserEntity.get());
        } else {
            return null;
        }
    }

    //유저 정보 업데이트
    public void update(UserDto userDto) {
        userRepository.save(UserEntity.toUpdateuserEntity(userDto));
    }

    //유저 탈퇴
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


}