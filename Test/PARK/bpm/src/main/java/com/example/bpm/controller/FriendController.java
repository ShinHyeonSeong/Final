package com.example.bpm.controller;

import com.example.bpm.dto.UserDto;
import com.example.bpm.repository.UserRepository;
import com.example.bpm.service.ProjectService;
import com.example.bpm.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FriendController {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserService userService;
    @Autowired
    private final ProjectService projectService;

    @GetMapping("/friends")
    public String goFriends() {
        return "friends";
    }

    @GetMapping("/friendList")
    public String friendList(HttpSession httpSession, Model model) {
        String userEmail = (String) httpSession.getAttribute("loginEmail");

        log.info("세션 정상 작동");
        UserDto findDto = userService.findById(userEmail);
        log.info("id 1 데이터 정상 작동");
        List<FriendDto> friendDtoList = projectService.findAll(findDto.getUuid());
        log.info("id1 에대한 리스트 작동");
        model.addAttribute("friend", friendDtoList);
        return "list";
    }

    @GetMapping("/friendPlus")
    public String gofriendPlus() {
        return "friendPlus";
    }

    @PostMapping("/friendSearch")
    public String friendSearch(@ModelAttribute UserDto UserDto, Model model){
        UserDto searchDto = projectService.search(UserDto);
        model.addAttribute("searchFr", searchDto);
        return "searchResult";
    }



}