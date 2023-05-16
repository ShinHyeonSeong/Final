package com.example.calenderex;

import com.example.calenderex.service.CalenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/calendar")
public class CalenderController {

    @Autowired
    CalenderService calendarService;

    @RequestMapping //기본 페이지 표시
    public String viewCalendar() {
        return "calendarPage";
    }

    @GetMapping("/event") //ajax 데이터 전송 URL
    public @ResponseBody List<Map<String, Object>> getEvent() {
        return calendarService.getEventList();
    }

}

