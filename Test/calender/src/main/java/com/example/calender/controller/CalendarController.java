package com.example.calender.controller;

import com.example.calender.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class CalendarController {

    @Autowired
    CalendarService calendarService;


    @GetMapping("/calender") //기본 페이지 표시
    public String viewCalendar() {
        return "main";
    }

    @RequestMapping(value = "/calendar/event", method = {RequestMethod.GET})
    public @ResponseBody List<Map<String, Object>> getEvent() {
        return calendarService.getEventList();
    }

}