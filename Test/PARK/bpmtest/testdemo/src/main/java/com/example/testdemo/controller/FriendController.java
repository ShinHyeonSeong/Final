package com.example.testdemo.controller;

import com.example.testdemo.dto.AccountDto;
import com.example.testdemo.dto.FriendDto;
import com.example.testdemo.entity.FriendEntity;
import com.example.testdemo.repository.AccountRepository;
import com.example.testdemo.repository.FriendRepository;
import com.example.testdemo.service.AccountService;
import com.example.testdemo.service.FriendService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FriendController {
    private final AccountService accountService;
    private final FriendService friendService;
    private final AccountRepository accountRepository;
    private final FriendRepository friendRepository;

    @GetMapping("/index")
    public String index() {
        return "index";
    }


    @GetMapping("/save") //회원가입
    public String saveForm() {
        return "save";
    }

    @PostMapping("/save")//회원가입
    public String tosave(@ModelAttribute AccountDto accountDto) {
        System.out.println("tosave 실행");
        System.out.println("현재 유저 :" + accountDto);
        accountService.save(accountDto);
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String tologin(@ModelAttribute AccountDto accountDto, HttpSession httpSession) {
        AccountDto loginResult = accountService.login(accountDto);
        if (loginResult != null) {
            httpSession.setAttribute("loginEmail", loginResult.getEmail());
            return "main";
        } else {
            System.out.println("로그인에 실패하였습니다.");
            return "login";
        }
    }

    @GetMapping("/friends")
    public String goFriends() {
        return "friends";
    }

    @GetMapping("/friendList")
    public String friendList(HttpSession httpSession, Model model) {
        String accountEmail = (String) httpSession.getAttribute("loginEmail");
        log.info("세션 정상 작동");
        AccountDto accountDto = accountService.findById1(accountEmail);
        log.info("id 1 데이터 정상 작동");
        List<FriendDto> friendDtoList = friendService.findAll(accountDto.getAccountid());
        log.info("id1 에대한 리스트 작동");
        model.addAttribute("friend", friendDtoList);
        return "list";
    }

    @GetMapping("/friendPlus")
    public String gofriendPlus() {
        return "friendPlus";
    }

    @PostMapping("/friendPlus")
    public String friendPlus(@ModelAttribute AccountDto accountDto, Model model){
        AccountDto searchDto = friendService.search(accountDto);
        model.addAttribute("searchFr", searchDto);
        return "searchResult";
    }

    @GetMapping()
}