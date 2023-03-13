package com.example.bpm.controller;

import com.example.bpm.dto.UserDto;
import com.example.bpm.service.FriendService;
import com.example.bpm.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FriendController {
    private final UserService userService;
    private final FriendService friendService;

    @GetMapping("/friends")
    public String goFriends() {
        return "friends";
    }

    @GetMapping("/friendList")
    public String friendList(HttpSession httpSession, Model model) {
        String accountEmail = (String) httpSession.getAttribute("loginEmail");
        log.info("세션 정상 작동");
        UserDto UserDto = userService.findAll();
        log.info("id 1 데이터 정상 작동");
        List<FriendDto> friendDtoList = friendService.findAll(UserDto.getAccountid());
        log.info("id1 에대한 리스트 작동");
        model.addAttribute("friend", friendDtoList);
        return "list";
    }

    @GetMapping("/friendPlus")
    public String gofriendPlus() {
        return "friendPlus";
    }

    @PostMapping("/friendPlus")
    public String friendPlus(@ModelAttribute UserDto UserDto, Model model){
        UserDto searchDto = friendService.search(UserDto);
        model.addAttribute("searchFr", searchDto);
        return "searchResult";
    }

}