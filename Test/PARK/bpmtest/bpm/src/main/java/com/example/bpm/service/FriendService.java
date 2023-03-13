package com.example.bpm.service;

import com.example.bpm.dto.FriendDto;
import com.example.bpm.dto.UserDto;
import com.example.bpm.entity.FriendEntity;
import com.example.bpm.entity.UserEntity;
import com.example.bpm.repository.FriendRepository;
import com.example.bpm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    final private UserRepository userRepository;

    //전체 친구 리스트 찾기
    public List<FriendDto> findAll(String accountid) {//쿼리문 작성 필요
        //friend table에 id 하나로 모두 찾는 쿼리문 메서드 작성
        List<FriendEntity> friendEntityList = friendRepository.findById1(accountid);
        List<FriendDto> friendDtoList = new ArrayList<>();
        for (FriendEntity friendEntity : friendEntityList) {
            friendDtoList.add(FriendDto.toFriendDto(friendEntity));
        }
        return friendDtoList;
    }

    //단일 친구 검색
    public UserDto search(UserDto userDto) {
        Optional<UserEntity> searchEntity = userRepository.findById(userDto.getUuid());
        if (searchEntity.isPresent()) {
            log.info("찾은 친구 :가 있습니다 ");

            UserDto searchDto = new UserDto();
            return (searchDto.toUserDto(searchEntity.get()));
        } else {
            log.info("찾으신 친구 검색 결과가 없습니다.");
            return null;
        }
    }

}

