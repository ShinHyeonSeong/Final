package com.example.bpm.service;

import com.example.bpm.dto.ProjectRequestDto;
import com.example.bpm.dto.ProjectRoleDto;
import com.example.bpm.dto.UserDto;
import com.example.bpm.entity.UserEntity;
import com.example.bpm.repository.ProjectRequestRepository;
import com.example.bpm.repository.UserRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@Builder
public class UserService {

    final private ProjectRequestRepository projectRequestRepository;
    final private UserRepository userRepository;

    //회원가입
    public UserDto save(UserDto userDto) {
        UserEntity userEntity = UserEntity.toUserEntity(userDto);
        userRepository.save(userEntity);
        log.info("회원 가입 성공 (서비스 동작)");
        return UserDto.toUserDto(userEntity);
    }

    
    //로그인
    public UserDto login(UserDto userDto) {
        Optional<UserEntity> byUserId = userRepository.findById(userDto.getUuid());
        if (byUserId.isPresent()) {
            //Optional로 묶여진 byUserEmail 값을 Entity로 변환
            UserEntity userEntity = byUserId.get();
            if (userEntity.getPassword().equals(userDto.getPassword())) {
                //Entity -> DTO 변환
                log.info("로그인 성공 (서비스 동작)");
                UserDto result = userDto.toUserDto(userEntity);
                return result;
            } else {

                log.info("로그인 실패 -> 이메일 비번이 안맞다 이말이야(서비스 동작)");
                return null;
            }
        } else {

            log.info("로그인 실패 -> 존재하지 않는 이뭬일 (서비스 동작)");
            return null;
        }

    }

    //ID로 회원 정보를 찾기 프로필 조회 or 회원 초대 시 필요함
    public UserDto findById(String id) {
        Optional<UserEntity> findId = userRepository.findById(id);
        if (findId.isPresent()) {
            UserEntity userEntity = findId.get();
            log.info(userEntity.getEmail() + "의 아이디를 찾았습니다(서비스 동작)");
            return UserDto.toUserDto(userEntity);
        } else {
            log.info("찾으신 결과가 없습니다 (서비스 동작)");
            return null;
        }
    }

    //회원정보 변경을 위한 정보 불러오기
    public UserDto updateForm(String myId) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(myId);
        if (optionalUserEntity.isPresent()) {
            log.info("회원정보 찾기 성공 회원 수정페이지를 엽니다 (서비스 작동)");
            return UserDto.toUserDto(optionalUserEntity.get());
        } else {
            log.info("회원정보를 못찾아 수정페이지를 못연다 (서비스 작동)");
            return null;
        }

    }

    //회원 정보 변경 저ㅏㅇ
    public void update(UserDto userDto) {
        userRepository.save(UserEntity.toUpdateuserEntity(userDto));
        log.info("회원 정보 업데이트에 성공하였습니다 (서비스 작동)");
    }

    //회원 탈퇴
    public void deleteById(String id) {
        userRepository.deleteById(id);
        log.info("회원 정보를 정상 삭제하였습니다 (서비스 작동)");
    }

    //프로젝트 초대 return 은 해당 send와 recv가 되어있는 projectRequest 데이터 값
    public ProjectRequestDto sendInvite(UserDto sendUser, UserDto recvUser) {
        if (sendUser != null && recvUser != null) {
            projectRequestRepository.plusProjectRequest(sendUser.getUuid(), recvUser.getUuid());
            log.info("친구 요청 정상 작동 (서비스 작동)");
            //만약 정상 친구요청이 되면 그 row을 확인할 수 있게 return 한다
            return ProjectRequestDto.toProjectRequestDto(projectRequestRepository.selectToRequestRow(sendUser.getUuid(), recvUser.getUuid()));
        } else {
            log.info("Dto NULL 값 (서비스 작동)");
            return null;
        }
    }

    //프로젝트 요청 수락
    //현재 session ID가 recvID로 되어있으면 그 관련 projectID를 가져온다 어떻게? (이게 테이블 연결이 안되어있음 - ProjectRequst테이블에 projectiD 컬럼값이 하나 더 있어야한다)
    // 그러면 ProjectRequst에는 projectId, sendId, recvID 가 존재하므로 send가 보낸 사람이 어떤 프로젝트 인지 정보도 저장되어 있으므로 바로 Role 부여 가능
    //projectID와 recvID 가 존재하고 일치하면
    //로직처리로 수락을 만든다
    //수락이 확인되면 projectID가 일치하는 프로젝트에 role 값을 부여한다.
    public ProjectRoleDto submitInvite(String recvUUID, ){

    }
}
