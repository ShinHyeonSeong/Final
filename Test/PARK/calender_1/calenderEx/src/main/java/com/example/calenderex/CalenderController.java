package com.example.calenderex;

import com.example.calenderex.CalenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class CalenderController {

    @Autowired
    CalenderService calendarService;

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}

