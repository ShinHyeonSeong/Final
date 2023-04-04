package com.example.bpm.service;

import com.example.bpm.dto.ProjectRequestDto;
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
    private final ProjectRequestRepository projectRequestRepository;

    public UserService(ProjectRequestRepository projectRequestRepository) {
        this.projectRequestRepository = projectRequestRepository;
    }

    final private UserRepository userRepository;

    //회원가입
    public UserDto save(UserDto userDto) {
        UserEntity userEntity = UserEntity.toUserEntity(userDto);
        userRepository.save(userEntity);
        log.info("회원 가입 성공 (서비스 동작)");
        return UserDto.toUserDto(userEntity);
    }

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

    public void update(UserDto userDto) {
        userRepository.save(UserEntity.toUpdateuserEntity(userDto));
        log.info("회원 정보 업데이트에 성공하였습니다 (서비스 작동)");
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
        log.info("회원 정보를 정상 삭제하였습니다 (서비스 작동)");
    }

    //프로젝트 초대
    public ProjectRequestDto sendInvite(UserDto sendUser, UserDto recvUser) {
        if (sendUser != null && recvUser != null) {
            projectRequestRepository.plusProject(sendUser.getUuid(), recvUser.getUuid());
            log.info("친구 요청 정상 작동 (서비스 작동)");
            projectRequestRepository.selectById(());
            return
        } else {
            log.info("Dto NULL 값 (서비스 작동)");
            return null;
        }
    }
}
