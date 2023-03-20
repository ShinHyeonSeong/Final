package com.example.bpm.service;

import com.example.bpm.dto.FriendDto;
import com.example.bpm.dto.FriendRequestDto;
import com.example.bpm.dto.UserDto;
import com.example.bpm.entity.FriendEntity;
import com.example.bpm.entity.FriendRequestEntity;
import com.example.bpm.entity.UserEntity;
import com.example.bpm.repository.FriendRepository;
import com.example.bpm.repository.FriendRequestRepository;
import com.example.bpm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;

    //전체 친구 리스트 찾기
    public List<FriendDto> findAll(Long userid) {//쿼리문 작성 필요
        //friend table에 id 하나로 모두 찾는 쿼리문 메서드 작성
        List<FriendEntity> friendEntityList = friendRepository.findById1(userid);
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

    //친구요청 매개변수 = 요청한유저 ,요청 받은 유저
    //친구 요청 테이블에 요청을 한 id 와 요청을 받은 id를 요청 테이블에 추가 ->
    //요청함 확인 시 요청테이블에 recvid에 일치하는 요청 출력
    //수락 시 친구리스트로 아이디들을 넘겨야함
    //고려할점 요청된 목록과 요청 온 목록을 다 출력할 줄 알아야함
    public void requestFriend(UserDto userDto1, UserDto userDto2) {
        Long sendID = userDto1.getUuid();
        Long recvID = userDto2.getUuid();
        if (sendID != null & recvID != null) {
            //DB에 저장만함
            friendRequestRepository.plusFriend(sendID, recvID);
            log.info("요청 테이블에 정상 입력 되었음" + sendID + recvID);
        } else {
            log.info("친구 요청 테이블에 요청 못함");
        }
    }

    //친구 요청함 리스트
    //세션 ID 기준으로 요청함 테이블을 불러온다
    public List<FriendRequestDto> friendRequestDtoList(Long userId) {
        //friend table에 id 하나로 모두 찾는 쿼리문 메서드 작성
        List<FriendEntity> friendEntityList = friendRequestRepository.findById1(userid);
        List<FriendDto> friendDtoList = new ArrayList<>();
        for (FriendEntity friendEntity : friendEntityList) {
            friendDtoList.add(FriendDto.toFriendDto(friendEntity));
        }
        return friendDtoList;
    }

    //친구 수락 거절 Controller에서 Session 유저는 요청을 받은 유저이다
    // 매개변수는 요청을 한 사람 정보와 수락 거절을 선택한 bool 타입
    public void permitFriend(FriendRequestDto sendfriendRequestDto, FriendRequestDto recvfriendRequestDto, Boolean permit) {
        //유저를 DB에서 찾는다.
        UserEntity sendUser = sendfriendRequestDto.getRecvUUID();
        UserEntity recvUser = recvfriendRequestDto.getRecvUUID();
        //두개의 변수가 일치하는 행을 찾는다
        FriendRequestEntity findRow = friendRequestRepository.findRequestRow(sendUser.getUuid(), recvUser.getUuid());
        if (permit == true) {
            friendRepository.insertFriend(sendUser.getUuid(), recvUser.getUuid());
            log.info("senduser = " + sendUser.getName() + "recvUser = " + recvUser.getName() + "친구 정상 추가");
        } else {
            log.info("친구 요청 거절");
            friendRequestRepository.deleteRow(sendUser.getUuid(), recvUser.getUuid());
        }
    }

    //친구삭제
    //삭제할 친구 ID를 받고 친구 테이블에서 id1과 id2 가 일치하는 행 삭제 리턴은 친구리스트
    public UserDto deleteFriend(UserDto userDto) {

    }
}

