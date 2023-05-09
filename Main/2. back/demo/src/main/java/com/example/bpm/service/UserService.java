package com.example.bpm.service;

import com.example.bpm.dto.UserDto;
import com.example.bpm.entity.ProjectRoleEntity;
import com.example.bpm.entity.UserEntity;
import com.example.bpm.repository.ProjectRoleRepository;
import com.example.bpm.repository.UserRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
//메서드 리턴타입은 무조건 Dto로 나오게 하자.
//파라미터는 String 형식으로 받기로 하자 (절대 객체단위로 받으면 안됨 헷갈리기도 하고 NULL 오류가 잘 생김)
public class UserService {
    @Autowired
    final private UserRepository userRepository;
    @Autowired
    final private ProjectRoleRepository projectRoleRepository;

    //회원가입
    public UserDto save(UserDto userDto) {
        UserEntity userEntity = UserEntity.toUserEntity(userDto);
        Optional<UserEntity> userResult = userRepository.findByEmail(userDto.getEmail());
        if (userResult.isPresent()) {
            log.info("이미 있는 이메일이다 회원가입 실패! (서비스 작동)");
            return null;
        } else {
            userRepository.save(userEntity);
            log.info("회원 가입 성공 (서비스 동작)");
            return UserDto.toUserDto(userEntity);
        }
    }


    //로그인
    public UserDto login(String email, String password) {
        Optional<UserEntity> findUser = userRepository.findByEmail(email);
        UserEntity loginUser = findUser.get();
        if (loginUser.getEmail().equals(email) && loginUser.getPassword().equals(password)) {
            log.info("이메일 && 패스워드 일치 로그인 성공 ");
            return UserDto.toUserDto(loginUser);
        } else {
            log.info("로그인 실패 -> 존재하지 않는 이뭬일 패스워드 (서비스 동작)");
            return null;
        }
    }

    //ID로 회원 정보를 찾기 프로필 조회 or 회원 초대 시 필요함
    public UserDto findByUser(String id) {
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
            log.info("회원정보 찾기 성공 회원 수정페이지를 엽니다 (updateForm() 서비스 작동)");
            return UserDto.toUserDto(optionalUserEntity.get());
        } else {
            log.info("회원정보를 못찾아 수정페이지를 못연다 (updateForm () 서비스 작동)");
            return null;
        }

    }

    //회원 정보 변경 저장
    public UserDto update(UserDto dto, String email, String name) {
        dto.setEmail(email);
        dto.setName(name);
        UserEntity afterEntity = UserEntity.toUserEntity(dto);
        UserEntity saveEntity = userRepository.save(afterEntity);
        return UserDto.toUserDto(saveEntity);
        //DB에 uuid값과 일치하는 정보를 가져와 email, password, name 만 수정해준다
//        Optional<UserEntity> beforeUser = userRepository.findById(uuid);
//        if (beforeUser.isPresent()) {
//            UserEntity beforUserEntity = beforeUser.get();
//            log.info("정보를 찾음 (update() 서비스 작동)");
//            UserEntity afterUser = userRepository.save(beforUserEntity.toUpdateuserEntity(email, name));
//            log.info("회원 정보 업데이트에 성공하였습니다 (서비스 작동)");
//            return UserDto.toUserDto(afterUser);
//        } else {
//            log.info("정보를 찾지 못함 (update() 서비스 작동);");
//            return null;
//        }
    }

    // 비밀번호 변경 메서드. 각 결과 번호에 대응하는 alert 메시지 전송 필요.
    public int changePassword(UserDto userDto, String email, String password,
                                 String newPassword, String confirmPassword) {
        UserEntity userEntity = userRepository.findById(userDto.getUuid()).get();
        if (userEntity.getEmail().equals(email)) {
            log.info("이메일 일치");
            if (userEntity.getPassword().equals(password)) {
                log.info("패스워드 일치");
                if (newPassword.equals(confirmPassword)) {
                    log.info("새로운 패스워드 일치");
                    userEntity.setPassword(newPassword);
                    userRepository.save(userEntity);
                    return 0;
                } else { return 3; }
            }else { return 2; }
        } else { return 1; }
    }

    //회원 탈퇴
    public void deleteById(String id) {
        userRepository.deleteById(id);
        log.info("회원 정보를 정상 삭제하였습니다 (서비스 작동)");
    }

    public List<UserDto> searchUser(String searchKeyword) {
        List<UserEntity> entities = userRepository.findAllByEmailContaining(searchKeyword);
        log.info("조회 완료, " + entities.isEmpty());
        List<UserDto> dtoList = new ArrayList<UserDto>();
        for (UserEntity entity : entities) {
            log.info(entity.getEmail());
            dtoList.add(UserDto.toUserDto(entity));
        }
        return dtoList;
    }

    public List<UserDto> searchUserToProject(Long id) {
        List<ProjectRoleEntity> projectRoleEntities = projectRoleRepository.userForProject(id);
        List<UserDto> userDtos = new ArrayList<UserDto>() {
        };
        for(ProjectRoleEntity projectRoleEntity : projectRoleEntities) {
            Optional<UserEntity> userEntity = userRepository.findById(projectRoleEntity.getUuidInRole().getUuid());
            userDtos.add(UserDto.toUserDto(userEntity.get()));
        }
        return userDtos;
    }
}

